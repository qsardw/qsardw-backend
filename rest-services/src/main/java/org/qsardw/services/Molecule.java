/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
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
 * @author Javier Caride Ulloa <javier.caride@qsardw.org>
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
