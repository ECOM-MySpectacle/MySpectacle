package org.applicationn.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;

import org.applicationn.domain.ArtisteEntity;
import org.applicationn.domain.RepresentationEntity;
import org.applicationn.domain.SpectacleEntity;
import org.applicationn.domain.SpectacleImage;
import org.applicationn.service.ArtisteService;
import org.applicationn.service.RepresentationService;
import org.applicationn.service.SpectacleService;

@Path("/spectacles")
@Named
public class SpectacleResource implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Inject
	private SpectacleService spectacleService;

	@Inject
	private ArtisteService artisteService;

	@Inject
	private RepresentationService representationService;

	/**
	 * Get the complete list of Spectacle Entries <br/>
	 * HTTP Method: GET <br/>
	 * Example URL: /spectacles
	 *
	 * @return List of SpectacleEntity (JSON)
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<SpectacleEntity> getAllSpectacles()
	{
		return spectacleService.findAllSpectacleEntities();
	}

	/**
	 * Get the number of Spectacle Entries <br/>
	 * HTTP Method: GET <br/>
	 * Example URL: /spectacles/count
	 *
	 * @return Number of SpectacleEntity
	 */
	@GET
	@Path("count")
	@Produces(MediaType.APPLICATION_JSON)
	public long getCount()
	{
		return spectacleService.countAllEntries();
	}

	/**
	 * Get a Spectacle Entity <br/>
	 * HTTP Method: GET <br/>
	 * Example URL: /spectacles/3
	 *
	 * @param id
	 * @return A Spectacle Entity (JSON)
	 */
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public SpectacleEntity getSpectacleById(@PathParam("id") Long id)
	{
		return spectacleService.find(id);
	}

	/**
	 * Create a Spectacle Entity <br/>
	 * HTTP Method: POST <br/>
	 * POST Request Body: New SpectacleEntity (JSON) <br/>
	 * Example URL: /spectacles
	 *
	 * @param spectacle
	 * @return A SpectacleEntity (JSON)
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SpectacleEntity addSpectacle(SpectacleEntity spectacle)
	{
		return spectacleService.save(spectacle);
	}

	/**
	 * Update an existing Spectacle Entity <br/>
	 * HTTP Method: PUT <br/>
	 * PUT Request Body: Updated SpectacleEntity (JSON) <br/>
	 * Example URL: /spectacles
	 *
	 * @param spectacle
	 * @return A SpectacleEntity (JSON)
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SpectacleEntity updateSpectacle(SpectacleEntity spectacle)
	{
		return spectacleService.update(spectacle);
	}

	/**
	 * Delete an existing Spectacle Entity <br/>
	 * HTTP Method: DELETE <br/>
	 * Example URL: /spectacles/3
	 *
	 * @param id
	 */
	@Path("{id}")
	@DELETE
	public void deleteSpectacle(@PathParam("id") Long id)
	{
		SpectacleEntity spectacle = spectacleService.find(id);
		spectacleService.delete(spectacle);
	}

	/**
	 * Get the list of Artistess that is assigned to a Spectacle <br/>
	 * HTTP Method: GET <br/>
	 * Example URL: /spectacles/3/artistess
	 *
	 * @param spectacleId
	 * @return List of ArtisteEntity
	 */
	@GET
	@Path("{id}/artistess")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ArtisteEntity> getArtistess(@PathParam("id") Long spectacleId)
	{
		SpectacleEntity spectacle = spectacleService.find(spectacleId);
		spectacle = spectacleService.fetchArtistess(spectacle);
		return spectacle.getArtistess();
	}

	/**
	 * Assign an existing Artistes to an existing Spectacle <br/>
	 * HTTP Method: PUT <br/>
	 * PUT Request Body: empty <br/>
	 * Example URL: /spectacles/3/artistess/8
	 *
	 * @param spectacleId
	 * @param artistesId
	 */
	@PUT
	@Path("{id}/artistess/{artistesId}")
	public void assignArtistes(@PathParam("id") Long spectacleId, @PathParam("artistesId") Long artistesId)
	{

		SpectacleEntity spectacle = spectacleService.find(spectacleId);
		ArtisteEntity artistes = artisteService.find(artistesId);

		spectacle = spectacleService.fetchArtistess(spectacle);
		spectacle.getArtistess().add(artistes);
		spectacleService.update(spectacle);

	}

	/**
	 * Remove a Spectacle-to-Artistes Assignment <br/>
	 * HTTP Method: DELETE <br/>
	 * Example URL: /spectacles/3/artistess/8
	 *
	 * @param spectacleId
	 * @param artistesId
	 */
	@DELETE
	@Path("{id}/artistess/{artistesId}")
	public void unassignArtistes(@PathParam("id") Long spectacleId, @PathParam("artistesId") Long artistesId)
	{

		SpectacleEntity spectacle = spectacleService.find(spectacleId);
		ArtisteEntity artistes = artisteService.find(artistesId);

		spectacle = spectacleService.fetchArtistess(spectacle);
		spectacle.getArtistess().remove(artistes);
		spectacleService.update(spectacle);

	}

	@GET
	@Path("{id}/representations")
	@Produces(MediaType.APPLICATION_JSON)
	public List<RepresentationEntity> getRepresentations(@PathParam("id") Long spectacleId)
	{
		SpectacleEntity spectacle = spectacleService.find(spectacleId);

		return representationService.findRepresentationsBySpectacle(spectacle);
	}

	@GET
	@Path("{id}/image")
	@Produces(MediaType.APPLICATION_JSON)
	public SpectacleImage getImage(@PathParam("id") Long spectacleId)
	{
		SpectacleEntity spectacle = spectacleService.find(spectacleId);

		return spectacleService.lazilyLoadImageToSpectacle(spectacle).getImage();
	}
}
