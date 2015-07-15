/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package org.qsardw.services;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import org.qsardw.datamodel.beans.Dataset;
import org.qsardw.datamodel.beans.DatasetRawMolecule;
import org.qsardw.datamodel.beans.DatasetRawMoleculeGroup;
import org.qsardw.datamodel.dao.DatasetDAO;
import org.qsardw.datamodel.dao.DatasetRawMoleculesDAO;
import org.qsardw.datamodel.dao.DatasetRawMoleculesGroupsDAO;
import org.qsardw.services.responses.RawMoleculesCollection;
import org.qsardw.services.responses.RawMoleculesGroupsCollection;

/**
 * @author Javier Caride Ulloa <javier.caride@qsardw.org>
 */
@Path("dataset")
public class SingleDataset {
    /**
     * Root logger
     */
    protected static Logger logger = Logger.getLogger(SingleDataset.class);
    
    /**
     * Returns a dataset object. It returns a complete dataset object
     *
     * @param datasetId
     * @return JSON Dataset object
     */
    @GET
    @Path("{datasetId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Dataset listAll(@PathParam("datasetId") Integer datasetId) {    
        DatasetDAO datasets = new DatasetDAO();
        return datasets.getById(datasetId);
    }
    
    /**
     * Returns a dataset object. It returns a complete dataset object
     *
     * @param datasetId
     * @param limit
     * @param offset
     * @return JSON Dataset object
     */
    @GET
    @Path("{datasetId}/cleanmolecules")
    @Produces(MediaType.APPLICATION_JSON)
    public RawMoleculesCollection getCleanMolecules(
            @PathParam("datasetId") Integer datasetId,
            @QueryParam("limit") int limit,
            @QueryParam("offset") int offset
    ) {
        DatasetRawMoleculesDAO datasetMolecules = new DatasetRawMoleculesDAO();
        
        List<DatasetRawMolecule> molecules = datasetMolecules.selectCleanMolecules(datasetId, limit, offset);
        int moleculesNumber = molecules.size();
        
        RawMoleculesCollection response = new RawMoleculesCollection();
        response.setTotalMolecules(moleculesNumber);
        response.setMolecules(molecules);
        
        return response;
    }
    
    /**
     * Returns a dataset object. It returns a complete dataset object
     *
     * @param datasetId
     * @param limit
     * @param offset
     * @return JSON Dataset object
     */
    @GET
    @Path("{datasetId}/discardedmolecules")
    @Produces(MediaType.APPLICATION_JSON)
    public RawMoleculesCollection getDiscardedMolecules(
            @PathParam("datasetId") Integer datasetId,
            @QueryParam("limit") int limit,
            @QueryParam("offset") int offset
    ) {
        DatasetRawMoleculesDAO datasetMolecules = new DatasetRawMoleculesDAO();
        
        List<DatasetRawMolecule> molecules = datasetMolecules.selectDiscardedMolecules(datasetId, limit, offset);
        int moleculesNumber = molecules.size();
        
        RawMoleculesCollection response = new RawMoleculesCollection();
        response.setTotalMolecules(moleculesNumber);
        response.setMolecules(molecules);
        
        return response;
    }
    
    /**
     * Returns a dataset object. It returns a complete dataset object
     *
     * @param datasetId
     * @param limit
     * @param offset
     * @return JSON Dataset object
     */
    @GET
    @Path("{datasetId}/moleculestoreview")
    @Produces(MediaType.APPLICATION_JSON)
    public RawMoleculesCollection getMoleculesToReview(
            @PathParam("datasetId") Integer datasetId,
            @QueryParam("limit") int limit,
            @QueryParam("offset") int offset
    ) {
        DatasetRawMoleculesDAO datasetMolecules = new DatasetRawMoleculesDAO();
        
        List<DatasetRawMolecule> molecules = datasetMolecules.selectMoleculesToReview(datasetId, limit, offset);
        int moleculesNumber = molecules.size();
        
        RawMoleculesCollection response = new RawMoleculesCollection();
        response.setTotalMolecules(moleculesNumber);
        response.setMolecules(molecules);
        
        return response;
    }
    
    /**
     * Returns an array of multiple molecules to review
     *
     * @param datasetId
     * @param limit
     * @param offset
     * @return JSON Dataset object
     */
    @GET
    @Path("{datasetId}/multiplemoleculestoreview")
    @Produces(MediaType.APPLICATION_JSON)
    public RawMoleculesCollection getMultipleMoleculesToReview(
            @PathParam("datasetId") Integer datasetId,
            @QueryParam("limit") int limit,
            @QueryParam("offset") int offset
    ) {
        DatasetRawMoleculesDAO datasetMolecules = new DatasetRawMoleculesDAO();
        
        List<DatasetRawMolecule> molecules = datasetMolecules.selectMultipleMoleculesToReview(datasetId, limit, offset);
        int moleculesNumber = molecules.size();
        
        RawMoleculesCollection response = new RawMoleculesCollection();
        response.setTotalMolecules(moleculesNumber);
        response.setMolecules(molecules);
        
        return response;
    }
    
    /**
     * Returns a dataset object. It returns a complete dataset object
     *
     * @param datasetId
     * @param inchiKey
     * @param limit
     * @param offset
     * @return JSON Dataset object
     */
    @GET
    @Path("{datasetId}/duplicates/{inchiKey}")
    @Produces(MediaType.APPLICATION_JSON)
    public RawMoleculesCollection getInchiKeyDuplicates(
            @PathParam("datasetId") Integer datasetId,
            @PathParam("inchiKey") String inchiKey,
            @QueryParam("limit") int limit,
            @QueryParam("offset") int offset
    ) {
        DatasetRawMoleculesDAO datasetMolecules = new DatasetRawMoleculesDAO();
        
        List<DatasetRawMolecule> molecules = datasetMolecules.selectInchiKeyDuplicates(datasetId, inchiKey, limit, offset);
        int moleculesNumber = molecules.size();
        
        RawMoleculesCollection response = new RawMoleculesCollection();
        response.setTotalMolecules(moleculesNumber);
        response.setMolecules(molecules);
        
        return response;
    }
    
    /**
     * Returns the collection of groups that belong to a dataset
     * 
     * @param datasetId
     * @param limit
     * @param offset
     * @return 
     */
    @GET
    @Path("{datasetId}/groups")
    @Produces(MediaType.APPLICATION_JSON)
    public RawMoleculesGroupsCollection getGroups(
            @PathParam("datasetId") Integer datasetId,
            @QueryParam("limit") int limit,
            @QueryParam("offset") int offset
    ) {
        DatasetRawMoleculesGroupsDAO datasetGroups = new DatasetRawMoleculesGroupsDAO();
        
        List<DatasetRawMoleculeGroup> groups = datasetGroups.selectByDataset(datasetId, limit, offset);
        int groupsNumber = groups.size();
        
        RawMoleculesGroupsCollection response = new RawMoleculesGroupsCollection();
        response.setTotalGroups(groupsNumber);
        response.setGroups(groups);
        
        return response;
    }
    
    
    /**
     * Returns dataset molecules that belong to a group
     *
     * @param datasetId
     * @param moleculesGroup
     * @param limit
     * @param offset
     * @return JSON Dataset object
     */
    @GET
    @Path("{datasetId}/multiples/{moleculesGroup}")
    @Produces(MediaType.APPLICATION_JSON)
    public RawMoleculesCollection getMoleculesGroup(
            @PathParam("datasetId") Integer datasetId,
            @PathParam("moleculesGroup") Integer moleculesGroup,
            @QueryParam("limit") int limit,
            @QueryParam("offset") int offset
    ) {
        DatasetRawMoleculesDAO datasetMolecules = new DatasetRawMoleculesDAO();  
        
        List<DatasetRawMolecule> molecules = datasetMolecules.selectByMoleculesGroup(datasetId, moleculesGroup, limit, offset);
        int moleculesNumber = molecules.size();
        
        RawMoleculesCollection response = new RawMoleculesCollection();
        response.setTotalMolecules(moleculesNumber);
        response.setMolecules(molecules);
        
        return response;
    }
}
