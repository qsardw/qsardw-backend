package org.qsardw.backend.conversion;


import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmilesParser;
import org.qsardw.backend.exceptions.QsardwException;

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
