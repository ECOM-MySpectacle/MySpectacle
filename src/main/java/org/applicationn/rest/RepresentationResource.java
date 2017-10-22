package org.applicationn.rest;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.applicationn.domain.RepresentationEntity;
import org.applicationn.service.RepresentationService;

@Path("/representations")
@Named
public class RepresentationResource implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private RepresentationService representationService;
    
    /**
     * Get the complete list of Representation Entries <br/>
     * HTTP Method: GET <br/>
     * Example URL: /representations
     * @return List of RepresentationEntity (JSON)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<RepresentationEntity> getAllRepresentations() {
        return representationService.findAllRepresentationEntities();
    }
    
    /**
     * Get the number of Representation Entries <br/>
     * HTTP Method: GET <br/>
     * Example URL: /representations/count
     * @return Number of RepresentationEntity
     */
    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public long getCount() {
        return representationService.countAllEntries();
    }
    
    /**
     * Get a Representation Entity <br/>
     * HTTP Method: GET <br/>
     * Example URL: /representations/3
     * @param id
     * @return A Representation Entity (JSON)
     */
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public RepresentationEntity getRepresentationById(@PathParam("id") Long id) {
        return representationService.find(id);
    }
    
    /**
     * Create a Representation Entity <br/>
     * HTTP Method: POST <br/>
     * POST Request Body: New RepresentationEntity (JSON) <br/>
     * Example URL: /representations
     * @param representation
     * @return A RepresentationEntity (JSON)
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RepresentationEntity addRepresentation(RepresentationEntity representation) {
        return representationService.save(representation);
    }
    
    /**
     * Update an existing Representation Entity <br/>
     * HTTP Method: PUT <br/>
     * PUT Request Body: Updated RepresentationEntity (JSON) <br/>
     * Example URL: /representations
     * @param representation
     * @return A RepresentationEntity (JSON)
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RepresentationEntity updateRepresentation(RepresentationEntity representation) {
        return representationService.update(representation);
    }
    
    /**
     * Delete an existing Representation Entity <br/>
     * HTTP Method: DELETE <br/>
     * Example URL: /representations/3
     * @param id
     */
    @Path("{id}")
    @DELETE
    public void deleteRepresentation(@PathParam("id") Long id) {
        RepresentationEntity representation = representationService.find(id);
        representationService.delete(representation);
    }
    
}
