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

import org.applicationn.domain.SalleEntity;
import org.applicationn.service.SalleService;

@Path("/salles")
@Named
public class SalleResource implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private SalleService salleService;
    
    /**
     * Get the complete list of Salle Entries <br/>
     * HTTP Method: GET <br/>
     * Example URL: /salles
     * @return List of SalleEntity (JSON)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<SalleEntity> getAllSalles() {
        return salleService.findAllSalleEntities();
    }
    
    /**
     * Get the number of Salle Entries <br/>
     * HTTP Method: GET <br/>
     * Example URL: /salles/count
     * @return Number of SalleEntity
     */
    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public long getCount() {
        return salleService.countAllEntries();
    }
    
    /**
     * Get a Salle Entity <br/>
     * HTTP Method: GET <br/>
     * Example URL: /salles/3
     * @param id
     * @return A Salle Entity (JSON)
     */
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SalleEntity getSalleById(@PathParam("id") Long id) {
        return salleService.find(id);
    }
    
    /**
     * Create a Salle Entity <br/>
     * HTTP Method: POST <br/>
     * POST Request Body: New SalleEntity (JSON) <br/>
     * Example URL: /salles
     * @param salle
     * @return A SalleEntity (JSON)
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SalleEntity addSalle(SalleEntity salle) {
        return salleService.save(salle);
    }
    
    /**
     * Update an existing Salle Entity <br/>
     * HTTP Method: PUT <br/>
     * PUT Request Body: Updated SalleEntity (JSON) <br/>
     * Example URL: /salles
     * @param salle
     * @return A SalleEntity (JSON)
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SalleEntity updateSalle(SalleEntity salle) {
        return salleService.update(salle);
    }
    
    /**
     * Delete an existing Salle Entity <br/>
     * HTTP Method: DELETE <br/>
     * Example URL: /salles/3
     * @param id
     */
    @Path("{id}")
    @DELETE
    public void deleteSalle(@PathParam("id") Long id) {
        SalleEntity salle = salleService.find(id);
        salleService.delete(salle);
    }
    
}
