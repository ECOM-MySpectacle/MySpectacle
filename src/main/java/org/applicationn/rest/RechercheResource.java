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
import org.applicationn.search.criteria.InvalidFilterException;
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
	public String get()
	{
		return "{\"error\":\"Not allowed\"}";
	}

	private String badRequest()
	{
		return "{\"error\":\"Bad request\"}";
	}

	private String unknownFilter(String filter)
	{
		return "{\"error\":\"Unknown filter: " + filter + "\"}";
	}

	private String invalidFilter(String filter)
	{
		return "{\"error\":\"Invalid filter: " + filter + "\"}";
	}

	/**
	 * Get the list of Spectacle Entries matching the given criteria<br/>
	 * HTTP Method: POST<br/>
	 * PUT Request Body: a JSON object containing a page number "page", the number of elements per page "per_page"
	 * and the list of filters in the "filters" array<br/>
	 * URL: /recherche/representations
	 *
	 * @return List of RepresentationEntity (JSON)
	 */
	@Path("spectacles")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Object findSpectaclesByCriteria(String json)
	{
		return findByCriteria(json, new SpectacleSearch(service));
	}

	/**
	 * Get the list of Salle Entries matching the given criteria<br/>
	 * HTTP Method: POST<br/>
	 * PUT Request Body: a JSON object containing a page number "page", the number of elements per page "per_page"
	 * and the list of filters in the "filters" array<br/>
	 * URL: /recherche/representations
	 *
	 * @return List of RepresentationEntity (JSON)
	 */
	@Path("salles")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Object findSallesByCriteria(String json)
	{
		return findByCriteria(json, new SalleSearch(service));
	}

	/**
	 * Get the list of Artiste Entries matching the given criteria<br/>
	 * HTTP Method: POST<br/>
	 * PUT Request Body: a JSON object containing a page number "page", the number of elements per page "per_page"
	 * and the list of filters in the "filters" array<br/>
	 * URL: /recherche/representations
	 *
	 * @return List of RepresentationEntity (JSON)
	 */
	@Path("artistes")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Object findArtistesByCriteria(String json)
	{
		return findByCriteria(json, new ArtisteSearch(service));
	}

	/**
	 * Get the list of Representation Entries matching the given criteria<br/>
	 * HTTP Method: POST<br/>
	 * PUT Request Body: a JSON object containing a page number "page", the number of elements per page "per_page"
	 * and the list of filters in the "filters" array<br/>
	 * URL: /recherche/representations
	 *
	 * @return List of RepresentationEntity (JSON)
	 */
	@Path("representations")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Object findRepresentationsByCriteria(String json)
	{
		return findByCriteria(json, new RepresentationSearch(service));
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
		catch(InvalidFilterException e)
		{
			return invalidFilter(e.getFilter());
		}
		catch(Exception e)
		{
			return badRequest();
		}

		return search.find(params);
	}
}
