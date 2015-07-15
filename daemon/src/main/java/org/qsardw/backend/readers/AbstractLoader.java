/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.qsardw.backend.readers;

import org.apache.log4j.Logger;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.graph.ConnectivityChecker;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IAtomContainerSet;
import org.openscience.cdk.interfaces.IChemObjectBuilder;
import org.openscience.cdk.silent.SilentChemObjectBuilder;
import org.openscience.cdk.tools.CDKHydrogenAdder;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.qsardw.backend.conversion.InchiBuilder;
import org.qsardw.backend.conversion.InchiKeyBuilder;
import org.qsardw.backend.conversion.SmilesBuilder;
import org.qsardw.backend.depict.PngDepict;
import org.qsardw.backend.exceptions.QsardwException;
import org.qsardw.datamodel.beans.Dataset;
import org.qsardw.datamodel.beans.DatasetError;
import org.qsardw.datamodel.beans.DatasetRawMolecule;
import org.qsardw.datamodel.beans.DatasetRawMoleculeGroup;
import org.qsardw.datamodel.dao.DatasetDAO;
import org.qsardw.datamodel.dao.DatasetErrorsDAO;
import org.qsardw.datamodel.dao.DatasetRawMoleculesDAO;
import org.qsardw.datamodel.dao.DatasetRawMoleculesGroupsDAO;

import java.io.File;
import java.io.IOException;

/**
 * @author Javier Caride Ulloa <javier.caride@qsardw.org>
 */
public abstract class AbstractLoader {

    protected static final int NO_GROUP = 0;

    protected static final int TO_CLEAN = 1;
    protected static final int MULTIPLE_TO_REVIEW = 2;

    protected static final int ON_MULTIPLE_REVIEW = 1;
    protected static final int ON_MULTIPLE_KEEP_BIGGEST = 2;

    protected int datasetId;
    protected File datasetSourceFile;
    protected Dataset dataset;
    protected DatasetDAO datasetDao;
    protected DatasetRawMoleculesDAO rawMoleculesDao;
    protected DatasetRawMoleculesGroupsDAO rawMoleculesGroupsDao;

    public abstract Logger getLogger();

    public File getDatasetSourceFile() {
        return datasetSourceFile;
    }

    public final void setDatasetSourceFile(File datasetSourceFile) {
        this.datasetSourceFile = datasetSourceFile;
    }

    public int getDatasetId() {
        return datasetId;
    }

    public final void setDatasetId(int datasetId) {
        this.datasetId = datasetId;
        this.dataset = this.datasetDao.getById(datasetId);
    }

    protected void setDaos() {
        this.rawMoleculesDao = new DatasetRawMoleculesDAO();
        this.rawMoleculesGroupsDao = new DatasetRawMoleculesGroupsDAO();
        this.datasetDao = new DatasetDAO();
    }

    protected void addDatasetError(Exception ex, int moleculeNumber) {
        DatasetError datasetError = new DatasetError();
        datasetError.setDataset(this.getDatasetId());
        datasetError.setErrorMessage(ex.getMessage());
        datasetError.setMolecule(moleculeNumber);

        DatasetErrorsDAO errorDao = new DatasetErrorsDAO();
        errorDao.create(datasetError);
    }

    protected void processMolecule(IAtomContainer molecule, int moleculeNumber) {
        try {
            IChemObjectBuilder builder = SilentChemObjectBuilder.getInstance();
            AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(molecule);
            CDKHydrogenAdder.getInstance(builder).addImplicitHydrogens(molecule);

            if (ConnectivityChecker.isConnected(molecule)) {
                this.addRawMolecule(molecule, moleculeNumber, TO_CLEAN, NO_GROUP);
            } else {
                if (dataset.getMultipleMoleculesStrategy() == ON_MULTIPLE_KEEP_BIGGEST) {
                    IAtomContainer biggestMolecule = this.getBiggestMolecule(molecule);
                    this.addRawMolecule(biggestMolecule, moleculeNumber, TO_CLEAN, NO_GROUP);
                } else {
                    this.addMultipleMoleculesToReview(molecule, moleculeNumber);
                }
            }
        } catch (QsardwException | CDKException | IOException ex) {
            this.getLogger().debug(ex.getMessage());
            this.addDatasetError(ex, moleculeNumber);
        }
    }

    protected void addMultipleMoleculesToReview(IAtomContainer molecule, int moleculeNumber)
            throws QsardwException, CDKException, IOException {
        IAtomContainerSet molecules = ConnectivityChecker.partitionIntoMolecules(molecule);
        int numberOfMolecules = molecules.getAtomContainerCount();
        String groupSmile = SmilesBuilder.fromMolecule(molecule);

        // Create a group to identify the molecules
        DatasetRawMoleculeGroup group = new DatasetRawMoleculeGroup();
        group.setGroupSmile(groupSmile);
        group.setDataset(this.getDatasetId());
        this.rawMoleculesGroupsDao.create(group);

        for (int i = 0; i < numberOfMolecules; i++) {
            IAtomContainer splittedMolecule = molecules.getAtomContainer(i);
            this.addRawMolecule(splittedMolecule, moleculeNumber, MULTIPLE_TO_REVIEW, group.getId());
        }
    }

    /**
     * Returns the biggest molecule of a set of molecules
     *
     * @param molecule
     * @return
     */
    protected IAtomContainer getBiggestMolecule(IAtomContainer molecule) {
        IAtomContainerSet molecules = ConnectivityChecker.partitionIntoMolecules(molecule);
        int numberOfMolecules = molecules.getAtomContainerCount();

        IAtomContainer biggestMolecule = molecules.getAtomContainer(0);
        for (int i = 0; i < numberOfMolecules; i++) {
            IAtomContainer splittedMolecule = molecules.getAtomContainer(i);

            double biggestMass = AtomContainerManipulator.getNaturalExactMass(
                    biggestMolecule
            );
            double splittedMass = AtomContainerManipulator.getNaturalExactMass(
                    splittedMolecule
            );

            if (biggestMass < splittedMass) {
                biggestMolecule = splittedMolecule;
            }
        }

        return biggestMolecule;
    }

    protected void addRawMolecule(IAtomContainer molecule, int moleculeNumber, int status, int group)
            throws QsardwException, CDKException, IOException {
        String inchi = InchiBuilder.fromMolecule(molecule);
        String inchiKey = InchiKeyBuilder.fromMolecule(molecule);
        String smile = SmilesBuilder.fromMolecule(molecule);

        DatasetRawMolecule rawMolecule = new DatasetRawMolecule();
        rawMolecule.setDataset(this.getDatasetId());
        rawMolecule.setMoleculeNumber(moleculeNumber);
        rawMolecule.setInchi(inchi);
        rawMolecule.setInchiKey(inchiKey);
        rawMolecule.setSmile(smile);
        rawMolecule.setSourceId(this.getMoleculeSourceId(molecule));
        rawMolecule.setSourceName(this.getMoleculeSourceName(molecule));
        rawMolecule.setSourcePublication(this.getMoleculeSourcePublication(molecule));
        rawMolecule.setIsDuplicated(Boolean.FALSE);
        rawMolecule.setStatus(status);
        rawMolecule.setMoleculesGroup(group);

        this.rawMoleculesDao.create(rawMolecule);

        PngDepict depictManager = new PngDepict(this.getDatasetId());
        depictManager.setWidth(1024);
        depictManager.setHeight(768);

        depictManager.writeImage(molecule, rawMolecule.getId());
    }

    /**
     * Returns the molecule source identifier
     * @param molecule
     * @return
     */
    protected String getMoleculeSourceId(IAtomContainer molecule) {
        String moleculeSourceId = (String) molecule.getProperty("MoleculeSourceCode");
        if (moleculeSourceId == null) {
            moleculeSourceId = "";
        }

        return moleculeSourceId;
    }

    /**
     * Returns the source molecule name
     * @param molecule
     * @return
     */
    protected String getMoleculeSourceName(IAtomContainer molecule) {
        String moleculeSourceName = (String) molecule.getProperty("MoleculeName");
        if (moleculeSourceName == null) {
            moleculeSourceName = "";
        }

        return moleculeSourceName;
    }

    /**
     * Returns the source publication paper
     *
     * @param molecule
     * @return
     */
    protected String getMoleculeSourcePublication(IAtomContainer molecule) {
        String moleculeSourcePublication = (String) molecule.getProperty("MoleculeReference");
        if (moleculeSourcePublication == null) {
            moleculeSourcePublication = "";
        }

        return moleculeSourcePublication;
    }
}
