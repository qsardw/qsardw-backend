/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.qsardw.backend.conversion;

import org.qsardw.backend.exceptions.QsardwException;
import net.sf.jniinchi.INCHI_RET;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.inchi.InChIGenerator;
import org.openscience.cdk.inchi.InChIGeneratorFactory;
import org.openscience.cdk.interfaces.IAtomContainer;

/**
 * @author Javier Caride Ulloa <javier.caride@gmail.com>
 */
public class InchiKeyBuilder {
    public static String fromMolecule(IAtomContainer molecule) throws QsardwException {
        
        String inchiKey = "";

        try {
            InChIGeneratorFactory inchiGeneratorFactory = InChIGeneratorFactory.getInstance();
            InChIGenerator inchiGenerator = inchiGeneratorFactory.getInChIGenerator(molecule);
            INCHI_RET returnStatus = inchiGenerator.getReturnStatus();

            if ((returnStatus == INCHI_RET.OKAY) || (returnStatus == INCHI_RET.WARNING)) {
                inchiKey = inchiGenerator.getInchiKey();
            } else {
                throw new QsardwException("Structure generation failed: " + returnStatus.toString() + " [" + inchiGenerator.getMessage() + "]");
            }

        } catch (CDKException ex) {
            throw new QsardwException(ex);
        }
        return inchiKey;
    }
}
