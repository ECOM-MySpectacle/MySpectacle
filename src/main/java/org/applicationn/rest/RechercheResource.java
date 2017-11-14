package org.applicationn.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.List;

import org.applicationn.domain.ArtisteEntity;
import org.applicationn.domain.RepresentationEntity;
import org.applicationn.domain.SalleEntity;
import org.applicationn.domain.SpectacleEntity;
import org.applicationn.search.ArtisteSearch;
import org.applicationn.search.RepresentationSearch;
import org.applicationn.search.SalleSearch;
import org.applicationn.search.SpectacleSearch;
import org.applicationn.service.RechercheService;

@Path("/recherche")
@Named
public class RechercheResource implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Inject
	private RechercheService service;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIsNotAllowed()
	{
		return "Use POST.";
	}

	@Path("spectacles")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String noGetSpectacles()
	{
		return getIsNotAllowed();
	}

	@Path("artistes")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String noGetArtistes()
	{
		return getIsNotAllowed();
	}

	@Path("representations")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String noGetRepresentations()
	{
		return getIsNotAllowed();
	}

	@Path("salles")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String noGetSalles()
	{
		return getIsNotAllowed();
	}

	@Path("spectacles")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<SpectacleEntity> findSpectaclesByCriteria(String json)
	{
		SpectacleSearch search = new SpectacleSearch(service);

		search.createFilters(json);

		return search.find();
	}

	@Path("salles")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<SalleEntity> findSallesByCriteria(String json)
	{
		SalleSearch search = new SalleSearch(service);

		search.createFilters(json);

		return search.find();
	}

	@Path("artistes")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<ArtisteEntity> findArtistesByCriteria(String json)
	{
		ArtisteSearch search = new ArtisteSearch(service);

		search.createFilters(json);

		return search.find();
	}

	@Path("representations")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<RepresentationEntity> findRepresentationsByCriteria(String json)
	{
		RepresentationSearch search = new RepresentationSearch(service);

		search.createFilters(json);

		return search.find();
	}
}
