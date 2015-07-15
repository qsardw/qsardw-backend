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
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmilesGenerator;

/**
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
