package org.applicationn.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;

import org.applicationn.search.ArtisteSearch;
import org.applicationn.search.RepresentationSearch;
import org.applicationn.search.SalleSearch;
import org.applicationn.search.SpectacleSearch;
import org.applicationn.search.criteria.UnknownFilterException;
import org.applicationn.service.RechercheService;

@Path("/recherche")
@Named
public class RechercheResource implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Inject
	private RechercheService service;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getIsNotAllowed()
	{
		return "{\"error\":\"Use POST\"}";
	}

	@Path("spectacles")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String noGetSpectacles()
	{
		return getIsNotAllowed();
	}

	@Path("artistes")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String noGetArtistes()
	{
		return getIsNotAllowed();
	}

	@Path("representations")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String noGetRepresentations()
	{
		return getIsNotAllowed();
	}

	@Path("salles")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String noGetSalles()
	{
		return getIsNotAllowed();
	}

	private String badRequest()
	{
		return "{\"error\":\"Bad request\"}";
	}

	private String unknownFilter(String filter)
	{
		return "{\"error\":\"Unknown filter: " + filter + "\"}";
	}

	@Path("spectacles")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Object findSpectaclesByCriteria(String json)
	{
		SpectacleSearch search = new SpectacleSearch(service);

		try
		{
			search.createFilters(json);
		}
		catch(UnknownFilterException e)
		{
			return unknownFilter(e.getFilter());
		}
		catch(JsonException e)
		{
			return badRequest();
		}

		return search.find();
	}

	@Path("salles")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Object findSallesByCriteria(String json)
	{
		SalleSearch search = new SalleSearch(service);

		try
		{
			search.createFilters(json);
		}
		catch(UnknownFilterException e)
		{
			return unknownFilter(e.getFilter());
		}
		catch(JsonException e)
		{
			return badRequest();
		}

		return search.find();
	}

	@Path("artistes")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Object findArtistesByCriteria(String json)
	{
		ArtisteSearch search = new ArtisteSearch(service);

		try
		{
			search.createFilters(json);
		}
		catch(UnknownFilterException e)
		{
			return unknownFilter(e.getFilter());
		}
		catch(JsonException e)
		{
			return badRequest();
		}

		return search.find();
	}

	@Path("representations")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Object findRepresentationsByCriteria(String json)
	{
		RepresentationSearch search = new RepresentationSearch(service);

		try
		{
			search.createFilters(json);
		}
		catch(UnknownFilterException e)
		{
			return unknownFilter(e.getFilter());
		}
		catch(JsonException e)
		{
			return badRequest();
		}

		return search.find();
	}
}
