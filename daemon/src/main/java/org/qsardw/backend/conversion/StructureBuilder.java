/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.qsardw.backend.conversion;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmilesParser;
import org.qsardw.backend.exceptions.QsardwException;

/**
 * @author Javier Caride Ulloa <javier.caride@gmail.com>
 */
public class StructureBuilder {
    public static IAtomContainer fromSmiles(String smiles) throws QsardwException {
        SmilesParser parser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        try {
            return parser.parseSmiles(smiles);
        } catch (InvalidSmilesException ex) {
            throw new QsardwException("Structure generation failed: " + ex.getMessage());
        }
    }
}
