/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package org.qsardw.services;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.openscience.cdk.exception.CDKException;
import org.qsardw.datamodel.beans.Dataset;
import org.qsardw.datamodel.dao.DatasetDAO;
import org.qsardw.datamodel.writers.SdfWriter;
import org.qsardw.services.responses.SdfFile;

/**
 * @author Javier Caride Ulloa <javier.caride@qsardw.org>
 */
@Path("datasets")
public class Datasets {

    /**
     * Return all datasets. It returns an array with all the datasets
     * creatend in the system
     *
     * @return JSON Array of Dataset objects
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Dataset> listAll() {    
        DatasetDAO datasets = new DatasetDAO();
        return datasets.selectAll();
    }
    
    @GET
    @Path("{owner}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Dataset> list(@PathParam("owner") Integer owner) {
        DatasetDAO datasets = new DatasetDAO();
        return datasets.selectByOwner(owner);
    }
    
    @GET
    @Path("{id}/sdf")
    @Produces(MediaType.APPLICATION_JSON)
    public SdfFile getSdf(@PathParam("id") Integer id) {
        SdfWriter writer = new SdfWriter();
        
        SdfFile response = new SdfFile();
        try {
            response.setSdfFileContent(writer.datasetToSdf(id));
        } catch (CDKException | IOException ex) {
            Logger.getLogger(Datasets.class.getName()).log(Level.SEVERE, null, ex);
            response.setSdfFileContent("");
        } catch (Exception ex) {
            Logger.getLogger(Datasets.class.getName()).log(Level.SEVERE, null, ex);
            response.setSdfFileContent("");
        }
        return response;
        
    }
}

