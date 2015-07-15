/*
 * This file is part of the QSARDW Backend project
 *
 * (c) Javier Caride Ulloa <javier.caride@qsardw.org>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

package org.qsardw.services;

import java.util.Collection;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.qsardw.datamodel.beans.DatasetRawMoleculeGroup;
import org.qsardw.datamodel.dao.DatasetRawMoleculesGroupsDAO;

/**
 * @author Javier Caride Ulloa <javier.caride@qsardw.org>
 */
@Path("groups")
public class Groups {
    
    @GET
    @Path("dataset/{dataset}")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<DatasetRawMoleculeGroup> list(@PathParam("dataset") Integer dataset) {
        DatasetRawMoleculesGroupsDAO groups = new DatasetRawMoleculesGroupsDAO();
        return groups.selectByDataset(dataset);
    }
}

