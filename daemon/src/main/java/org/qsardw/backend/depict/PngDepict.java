/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */
package org.qsardw.backend.depict;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.vecmath.Vector2d;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.layout.TemplateHandler;
import org.openscience.cdk.renderer.AtomContainerRenderer;
import org.openscience.cdk.renderer.font.AWTFontManager;
import org.openscience.cdk.renderer.generators.BasicAtomGenerator;
import org.openscience.cdk.renderer.generators.BasicBondGenerator;
import org.openscience.cdk.renderer.generators.BasicSceneGenerator;
import org.openscience.cdk.renderer.visitor.AWTDrawVisitor;

/**
 * @author Javier Caride Ulloa <javier.caride@qsardw.org>
 */
public class PngDepict {
    
    private int width;
    private int height;
    private int datasetId;
    private String basePath;
    
    public PngDepict(int datasetId) {
        this.setDatasetId(datasetId);
        this.basePath = "/qsardw/data/images/"+ this.getDatasetId();
        File directory = new File(this.basePath);
        
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
    
    public void writeImage(IAtomContainer molecule, Integer moleculeId) throws CDKException, IOException {
        
        // the draw area and the image should be the same size
        Rectangle drawArea = new Rectangle(width, height);
        Image image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        StructureDiagramGenerator diagramGenerator = new StructureDiagramGenerator();
        diagramGenerator.setTemplateHandler(new TemplateHandler(DefaultChemObjectBuilder.getInstance()));
        diagramGenerator.setMolecule(molecule);
        diagramGenerator.generateCoordinates(new Vector2d(0,1));
        IAtomContainer moleculeToDepict = diagramGenerator.getMolecule();

        // generators make the image elements
        List generators = new ArrayList();
        generators.add(new BasicSceneGenerator());
        generators.add(new BasicBondGenerator());
        generators.add(new BasicAtomGenerator());

        // the renderer needs to have a toolkit-specific font manager
        AtomContainerRenderer renderer = new AtomContainerRenderer(generators, new AWTFontManager());

        // the call to 'setup' only needs to be done on the first paint                        
        renderer.setup(moleculeToDepict, drawArea);
        renderer.setScale(moleculeToDepict);
        

        // paint the background
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);
        
        renderer.paint(moleculeToDepict, new AWTDrawVisitor(g2));
        
        String filename = this.basePath + File.separator +"molecule_" + moleculeId + ".png";
        ImageIO.write((RenderedImage) image, "PNG", new File(filename));
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(int datasetId) {
        this.datasetId = datasetId;
    }
}
