/*
 * The MIT License
 *
 * Copyright 2014 Javier Caride Ulloa <javier.caride@gmail.com>.
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
 * Root resource (exposed at "echo" path)
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

