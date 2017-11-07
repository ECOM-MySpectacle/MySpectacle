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

import org.applicationn.domain.ArtisteEntity;
import org.applicationn.domain.SpectacleEntity;
import org.applicationn.service.ArtisteService;
import org.applicationn.service.SpectacleService;

@Path("/artistes")
@Named
public class ArtisteResource implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private ArtisteService artisteService;
    
    @Inject
    private SpectacleService spectacleService;
    
    /**
     * Get the complete list of Artiste Entries <br/>
     * HTTP Method: GET <br/>
     * Example URL: /artistes
     * @return List of ArtisteEntity (JSON)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ArtisteEntity> getAllArtistes() {
        return artisteService.findAllArtisteEntities();
    }
    
    /**
     * Get the number of Artiste Entries <br/>
     * HTTP Method: GET <br/>
     * Example URL: /artistes/count
     * @return Number of ArtisteEntity
     */
    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public long getCount() {
        return artisteService.countAllEntries();
    }
    
    /**
     * Get a Artiste Entity <br/>
     * HTTP Method: GET <br/>
     * Example URL: /artistes/3
     * @param id
     * @return A Artiste Entity (JSON)
     */
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ArtisteEntity getArtisteById(@PathParam("id") Long id) {
        return artisteService.find(id);
    }
    
    /**
     * Create a Artiste Entity <br/>
     * HTTP Method: POST <br/>
     * POST Request Body: New ArtisteEntity (JSON) <br/>
     * Example URL: /artistes
     * @param artiste
     * @return A ArtisteEntity (JSON)
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ArtisteEntity addArtiste(ArtisteEntity artiste) {
        return artisteService.save(artiste);
    }
    
    /**
     * Update an existing Artiste Entity <br/>
     * HTTP Method: PUT <br/>
     * PUT Request Body: Updated ArtisteEntity (JSON) <br/>
     * Example URL: /artistes
     * @param artiste
     * @return A ArtisteEntity (JSON)
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ArtisteEntity updateArtiste(ArtisteEntity artiste) {
        return artisteService.update(artiste);
    }
    
    /**
     * Delete an existing Artiste Entity <br/>
     * HTTP Method: DELETE <br/>
     * Example URL: /artistes/3
     * @param id
     */
    @Path("{id}")
    @DELETE
    public void deleteArtiste(@PathParam("id") Long id) {
        ArtisteEntity artiste = artisteService.find(id);
        artisteService.delete(artiste);
    }
    
    /**
     * Get the list of Spectacless that is assigned to a Artiste <br/>
     * HTTP Method: GET <br/>
     * Example URL: /artistes/3/spectacless
     * @param artisteId
     * @return List of SpectacleEntity
     */
    @GET
    @Path("{id}/spectacless")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SpectacleEntity> getSpectacless(@PathParam("id") Long artisteId) {
        ArtisteEntity artiste = artisteService.find(artisteId);
        artiste = artisteService.fetchSpectacless(artiste);
        return artiste.getSpectacless();
    }
    
    /**
     * Assign an existing Spectacles to an existing Artiste <br/>
     * HTTP Method: PUT <br/>
     * PUT Request Body: empty <br/>
     * Example URL: /artistes/3/spectacless/8
     * @param artisteId
     * @param spectaclesId
     */
    @PUT
    @Path("{id}/spectacless/{spectaclesId}")
    public void assignSpectacles(@PathParam("id") Long artisteId, @PathParam("spectaclesId") Long spectaclesId) {
        
        ArtisteEntity artiste = artisteService.find(artisteId);
        SpectacleEntity spectacles = spectacleService.find(spectaclesId);
        
        spectacles = spectacleService.fetchArtistess(spectacles);
        spectacles.getArtistess().add(artiste);
        spectacleService.update(spectacles);
        
    }
    
    /**
     * Remove a Artiste-to-Spectacles Assignment <br/>
     * HTTP Method: DELETE <br/>
     * Example URL: /artistes/3/spectacless/8
     * @param artisteId
     * @param spectaclesId
     */
    @DELETE
    @Path("{id}/spectacless/{spectaclesId}")
    public void unassignSpectacles(@PathParam("id") Long artisteId, @PathParam("spectaclesId") Long spectaclesId) {

        ArtisteEntity artiste = artisteService.find(artisteId);
        SpectacleEntity spectacles = spectacleService.find(spectaclesId);
        
        spectacles = spectacleService.fetchArtistess(spectacles);
        spectacles.getArtistess().remove(artiste);
        spectacleService.update(spectacles);
        
    }
    
}
