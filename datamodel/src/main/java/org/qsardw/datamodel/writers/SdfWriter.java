/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.qsardw.datamodel.writers;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import javax.vecmath.Vector2d;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.SDFWriter;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.layout.TemplateHandler;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.qsardw.datamodel.beans.DatasetRawMolecule;
import org.qsardw.datamodel.dao.DatasetRawMoleculesDAO;

/**
 * @author Javier Caride Ulloa <javier.caride@qsardw.org>
 */
public class SdfWriter {
    public void SdfWriter() {
    }
    
    public String datasetToSdf(Integer datasetId) throws CDKException, IOException, Exception {
        StringWriter stringWriter = new StringWriter();
        SDFWriter sdfWriter = new SDFWriter(stringWriter);

        DatasetRawMoleculesDAO moleculesDao = new DatasetRawMoleculesDAO();
        List<DatasetRawMolecule> molecules = moleculesDao.selectCleanMolecules(datasetId);
        for (DatasetRawMolecule rawMolecule : molecules) {
            /*InChIToStructure parser = InChIGeneratorFactory.getInstance().getInChIToStructure(
                rawMolecule.getInchi(), 
                DefaultChemObjectBuilder.getInstance()
            );
            
            IAtomContainer cleanMolecule = parser.getAtomContainer();*/
            
            SmilesParser sparser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
            IAtomContainer cleanMolecule = sparser.parseSmiles(rawMolecule.getSmile());
            
            AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(cleanMolecule);
            AtomContainerManipulator.percieveAtomTypesAndConfigureUnsetProperties(cleanMolecule);
            cleanMolecule = this.get2DCoords(cleanMolecule);
            
            sdfWriter.write(cleanMolecule);            
        }
        
        sdfWriter.close();
        stringWriter.close();
        
        return stringWriter.toString();
    }
    
    private IAtomContainer get2DCoords(IAtomContainer molecule) throws Exception {
        StructureDiagramGenerator sdg = new StructureDiagramGenerator();
        
        sdg.setTemplateHandler(new TemplateHandler(DefaultChemObjectBuilder.getInstance()));
        sdg.setMolecule(molecule);
        sdg.generateCoordinates(new Vector2d(0, 1));
        return sdg.getMolecule();
    }
            
}
