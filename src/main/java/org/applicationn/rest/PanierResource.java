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

import org.applicationn.domain.PanierEntity;
import org.applicationn.service.PanierService;

@Path("/paniers")
@Named
public class PanierResource implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private PanierService panierService;
    
    /**
     * Get the complete list of Panier Entries <br/>
     * HTTP Method: GET <br/>
     * Example URL: /paniers
     * @return List of PanierEntity (JSON)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PanierEntity> getAllPaniers() {
        return panierService.findAllPanierEntities();
    }
    
    /**
     * Get the number of Panier Entries <br/>
     * HTTP Method: GET <br/>
     * Example URL: /paniers/count
     * @return Number of PanierEntity
     */
    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public long getCount() {
        return panierService.countAllEntries();
    }
    
    /**
     * Get a Panier Entity <br/>
     * HTTP Method: GET <br/>
     * Example URL: /paniers/3
     * @param id
     * @return A Panier Entity (JSON)
     */
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PanierEntity getPanierById(@PathParam("id") Long id) {
        return panierService.find(id);
    }
    
    /**
     * Create a Panier Entity <br/>
     * HTTP Method: POST <br/>
     * POST Request Body: New PanierEntity (JSON) <br/>
     * Example URL: /paniers
     * @param panier
     * @return A PanierEntity (JSON)
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PanierEntity addPanier(PanierEntity panier) {
        return panierService.save(panier);
    }
    
    /**
     * Update an existing Panier Entity <br/>
     * HTTP Method: PUT <br/>
     * PUT Request Body: Updated PanierEntity (JSON) <br/>
     * Example URL: /paniers
     * @param panier
     * @return A PanierEntity (JSON)
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public PanierEntity updatePanier(PanierEntity panier) {
        return panierService.update(panier);
    }
    
    /**
     * Delete an existing Panier Entity <br/>
     * HTTP Method: DELETE <br/>
     * Example URL: /paniers/3
     * @param id
     */
    @Path("{id}")
    @DELETE
    public void deletePanier(@PathParam("id") Long id) {
        PanierEntity panier = panierService.find(id);
        panierService.delete(panier);
    }
    
}
