
package org.qsardw.backend.conversion;

import org.qsardw.backend.exceptions.QsardwException;
import net.sf.jniinchi.INCHI_RET;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.inchi.InChIGenerator;
import org.openscience.cdk.inchi.InChIGeneratorFactory;
import org.openscience.cdk.interfaces.IAtomContainer;

/**
 *
 * @author Javier Caride Ulloa <javier.caride@gmail.com>
 */
public class InchiBuilder {
    public static String fromMolecule(IAtomContainer molecule) throws QsardwException {
        String inchi = "";
        try {
            InChIGeneratorFactory inchiGeneratorFactory = InChIGeneratorFactory.getInstance();
            InChIGenerator inchiGenerator = inchiGeneratorFactory.getInChIGenerator(molecule);
            INCHI_RET returnStatus = inchiGenerator.getReturnStatus();

            if ((returnStatus == INCHI_RET.OKAY) || (returnStatus == INCHI_RET.WARNING)) {
                inchi = inchiGenerator.getInchi();
            } else {
                throw new QsardwException("Structure generation failed: " + returnStatus.toString() + " [" 
                        + inchiGenerator.getMessage() + inchiGenerator.getLog() + "]");
            }

        } catch (CDKException ex) {
            throw new QsardwException(ex);
        }
        return inchi;
    }
}
