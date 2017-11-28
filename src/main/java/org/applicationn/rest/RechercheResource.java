package org.applicationn.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.io.StringReader;

import org.applicationn.search.*;
import org.applicationn.search.criteria.UnknownFilterException;
import org.applicationn.service.RechercheService;

@Path("/recherche")
@Named
public class RechercheResource implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Inject
	private RechercheService service;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String post()
	{
		return "{\"error\":\"Not part of the API\"}";
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getIsNotAllowed()
	{
		return "{\"error\":\"Not allowed\"}";
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

		return findByCriteria(json, search);
	}

	@Path("salles")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Object findSallesByCriteria(String json)
	{
		SalleSearch search = new SalleSearch(service);

		return findByCriteria(json, search);
	}

	@Path("artistes")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Object findArtistesByCriteria(String json)
	{
		ArtisteSearch search = new ArtisteSearch(service);

		return findByCriteria(json, search);
	}

	@Path("representations")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Object findRepresentationsByCriteria(String json)
	{
		RepresentationSearch search = new RepresentationSearch(service);

		return findByCriteria(json, search);
	}

	private Object findByCriteria(String json, Search search)
	{
		SearchParameters params;

		try
		{
			JsonObject o;

			try(JsonReader reader = Json.createReader(new StringReader(json)))
			{
				o = reader.readObject();
			}

			params = new SearchParameters();

			params.page = o.getInt("page");
			params.perPage = o.getInt("per_page");

			search.createFilters(o.getJsonArray("filters"));
		}
		catch(UnknownFilterException e)
		{
			return unknownFilter(e.getFilter());
		}
		catch(Exception e)
		{
			return badRequest();
		}

		return search.find(params);
	}
}
