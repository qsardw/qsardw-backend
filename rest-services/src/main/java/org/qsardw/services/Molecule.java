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

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.qsardw.datamodel.cleaners.DatasetCleaner;
import org.qsardw.datamodel.beans.DatasetProcessedMolecule;
import org.qsardw.datamodel.beans.DatasetRawMolecule;
import org.qsardw.datamodel.dao.DatasetProcessedMoleculesDAO;
import org.qsardw.datamodel.dao.DatasetRawMoleculesDAO;

/**
 *
 * @author Javier Caride Ulloa <javier.caride@gmail.com>
 */
@Path("molecule")
public class Molecule {
    
    /**
     * Retrieve a dataset molecule
     * @param id
     * @return 
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public DatasetRawMolecule readMolecule(@PathParam("id") Integer id) {    
        DatasetRawMoleculesDAO rawMoleculesDao = new DatasetRawMoleculesDAO();
        return rawMoleculesDao.selectById(id);
    }
    
    @PUT
    @Path("{id}/setduplicate")
    @Produces(MediaType.APPLICATION_JSON)
    public DatasetProcessedMolecule discardMolecule(@PathParam("id") Integer id) {
        DatasetProcessedMoleculesDAO processedDao = new DatasetProcessedMoleculesDAO();
        
        DatasetProcessedMolecule processedMolecule = processedDao.read(id);
        processedMolecule.setProcessedStatus(2);
        processedDao.update(processedMolecule);
        
        return processedMolecule;
    }
    
    @PUT
    @Path("{id}/setclean")
    @Produces(MediaType.APPLICATION_JSON)
    public DatasetProcessedMolecule setcleanMolecule(@PathParam("id") Integer id) {
        DatasetProcessedMoleculesDAO processedDao = new DatasetProcessedMoleculesDAO();
        
        DatasetProcessedMolecule processedMolecule = processedDao.read(id);
        processedMolecule.setProcessedStatus(1);
        processedDao.update(processedMolecule);
        
        return processedMolecule;
    }
    
    @PUT
    @Path("{id}/toreview")
    @Produces(MediaType.APPLICATION_JSON)
    public DatasetProcessedMolecule toreviewMolecule(@PathParam("id") Integer id) {
        DatasetProcessedMoleculesDAO processedDao = new DatasetProcessedMoleculesDAO();
        
        DatasetProcessedMolecule processedMolecule = processedDao.read(id);
        processedMolecule.setProcessedStatus(3);
        processedDao.update(processedMolecule);
        
        return processedMolecule;
    }
    
    @PUT
    @Path("{id}/setdeleted")
    @Produces(MediaType.APPLICATION_JSON)
    public DatasetProcessedMolecule deleteMolecule(@PathParam("id") Integer id) {
        DatasetProcessedMoleculesDAO processedDao = new DatasetProcessedMoleculesDAO();
        
        DatasetProcessedMolecule processedMolecule = processedDao.read(id);
        processedMolecule.setProcessedStatus(4);
        processedDao.update(processedMolecule);
        
        return processedMolecule;
    }
    
    @PUT
    @Path("{id}/clean")
    @Produces(MediaType.APPLICATION_JSON)
    public DatasetProcessedMolecule cleanMolecule(@PathParam("id") Integer id) {
        
        DatasetRawMoleculesDAO moleculesDao = new DatasetRawMoleculesDAO();
        DatasetRawMolecule rawMolecule = moleculesDao.selectById(id);
        
        if ((rawMolecule.getStatus() == DatasetRawMolecule.TO_CLEAN) ||
            (rawMolecule.getStatus() == DatasetRawMolecule.REVIEW_MULTIPLE)
        ) {        
            rawMolecule.setStatus(DatasetRawMolecule.REVIEW_MULTIPLE);
            moleculesDao.update(rawMolecule);
        
            DatasetCleaner cleaner = new DatasetCleaner(rawMolecule.getDataset());
            return cleaner.findDuplicates(rawMolecule);
        } else {
            DatasetProcessedMoleculesDAO processedMolecules = new DatasetProcessedMoleculesDAO();
            return processedMolecules.getByMoleculeId(rawMolecule.getId());
        }
    }
}
