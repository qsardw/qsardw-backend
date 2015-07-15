/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.qsardw.backend.readers;

import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.qsardw.backend.conversion.StructureBuilder;
import org.qsardw.backend.exceptions.QsardwException;
import uk.ac.cam.ch.wwmm.opsin.NameToStructure;
import uk.ac.cam.ch.wwmm.opsin.NameToStructureConfig;
import uk.ac.cam.ch.wwmm.opsin.OpsinResult;

/**
 * @author Javier Caride Ulloa <javier.caride@qsardw.org>
 */
public class CsvLoader extends AbstractLoader{

    private static Logger logger = Logger.getLogger(SdfLoader.class);
    
    public CsvLoader() {
        this.setDaos();
    }
    
    public CsvLoader(File csvFile, int datasetId) {
        this.setDatasetSourceFile(csvFile);
        this.setDaos();
        this.setDatasetId(datasetId);
    }

    public Logger getLogger() {
        return logger;
    }
    
    public void readFile() {
        try {
            CSVReader reader = new CSVReader(new FileReader(datasetSourceFile), ';', '"');
            String[] moleculeData;
            
            int moleculeCounter = 0;
            
            while ((moleculeData = reader.readNext()) != null) {
                moleculeCounter++;

                NameToStructure converter = NameToStructure.getInstance();
                NameToStructureConfig converterConfig = new NameToStructureConfig();

                OpsinResult result = converter.parseChemicalName(moleculeData[0], converterConfig);
                IAtomContainer molecule = StructureBuilder.fromSmiles(result.getSmiles());

                molecule.setProperty("MoleculeSourceCode", moleculeData[2]);
                molecule.setProperty("MoleculeName", moleculeData[0]);
                molecule.setProperty("MoleculeReference", moleculeData[1]);

                this.processMolecule(molecule, moleculeCounter);
            }            
        } catch (IOException | QsardwException ex) {
            logger.error(ex.getMessage());
        }
    }
}
