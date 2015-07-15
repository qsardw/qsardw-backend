
package org.qsardw.backend.conversion;

import org.qsardw.backend.exceptions.QsardwException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmilesGenerator;

/**
 *
 * @author Javier Caride Ulloa <javier.caride@gmail.com>
 */
public class SmilesBuilder {
    public static String fromMolecule(IAtomContainer molecule) throws QsardwException {
        String smiles = "";
        
        try {            
            SmilesGenerator smilesGenerator = SmilesGenerator.absolute();
            smiles = smilesGenerator.create(molecule);
        } catch(Exception ex) {
            throw new QsardwException("Structure generation failed: " + ex.getMessage());
        }
        
        return smiles;
    }
}
