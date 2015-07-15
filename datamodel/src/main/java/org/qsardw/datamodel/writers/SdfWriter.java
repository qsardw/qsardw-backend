/*
 * The MIT License
 *
 * Copyright 2014 Javier Caride Ulloa.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.qsardw.datamodel.writers;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import javax.vecmath.Vector2d;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.inchi.InChIGeneratorFactory;
import org.openscience.cdk.inchi.InChIToStructure;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.SDFWriter;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.layout.TemplateHandler;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.qsardw.datamodel.beans.DatasetRawMolecule;
import org.qsardw.datamodel.dao.DatasetRawMoleculesDAO;

/**
 *
 * @author Javier Caride Ulloa
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
