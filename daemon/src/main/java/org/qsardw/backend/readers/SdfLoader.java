/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.qsardw.backend.readers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.iterator.IteratingSDFReader;

/**
 * @author Javier Caride Ulloa <javier.caride@qsardw.org>
 */
public class SdfLoader extends AbstractLoader {


    private static Logger logger = Logger.getLogger(SdfLoader.class);

    public SdfLoader() {
        this.setDaos();
    }

    public SdfLoader(File sdfFile, int datasetId) {
        this.setDatasetSourceFile(sdfFile);
        this.setDaos();
        this.setDatasetId(datasetId);
    }

    public Logger getLogger() {
        return logger;
    }

    public void readFile() {
        try {
            InputStream sdfStream = new FileInputStream(this.datasetSourceFile);
            IteratingSDFReader sdfReader = new IteratingSDFReader(sdfStream, DefaultChemObjectBuilder.getInstance());

            int moleculeCounter = 0;

            while (sdfReader.hasNext()) {
                moleculeCounter++;
                IAtomContainer molecule = sdfReader.next();
                this.processMolecule(molecule, moleculeCounter);
            }
        } catch (FileNotFoundException ex) {
            logger.error(ex.getMessage());
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }


}
