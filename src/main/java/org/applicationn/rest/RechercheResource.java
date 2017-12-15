package org.applicationn.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.io.StringReader;
import java.util.Objects;

import org.applicationn.search.*;
import org.applicationn.search.criteria.InvalidFilterException;
import org.applicationn.search.criteria.UnknownFilterException;
import org.applicationn.service.*;
import org.applicationn.service.security.UserService;
import org.applicationn.test.DataSet;

@Path("/recherche")
@Named
public class RechercheResource implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Inject
	private RechercheService service;

	@Inject
	private ArtisteService artisteService;

	@Inject
	private SpectacleService spectacleService;

	@Inject
	private SalleService salleService;

	@Inject
	private RepresentationService representationService;

	@Inject
	private UserService userService;

	private static boolean populated = false;

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

	@Path("populate")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String populate(@QueryParam("force") Boolean force)
	{
		if(!populated || Objects.equals(force, Boolean.TRUE))
		{
			DataSet test = new DataSet(artisteService, spectacleService, salleService, representationService, userService);

			test.populate();

			populated = true;

			return "{\"error\":\"Success\"}";
		}

		return "{\"error\":\"Test set already initialized\"}";
	}

	/**
	 * Get the list of Spectacle Entries matching the given criteria<br/>
	 * HTTP Method: POST<br/>
	 * POST Request Body: a JSON object containing a page number "page", the number of elements per page "per_page" and the list of filters in the "filters" array<br/>
	 * URL: /recherche/representations
	 * DATA: {"page":&lt;page number&gt;,"per_page":&lt;elements per page&gt;,"filters":{&lt;list of filters&gt;}}<br/>
	 * Available filters:
	 * <ul>
	 * <li>artist: name of the artist</li>
	 * <li>description: spectacle description</li>
	 * <li>genre: list of spectacle genres</li>
	 * <li>name: spectacle name</li>
	 * <li>public: list of targetted audience</li>
	 * <li>theme: spectacle theme</li>
	 * </ul>
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
	 * POST Request Body: a JSON object containing a page number "page", the number of elements per page "per_page" and the list of filters in the "filters" array<br/>
	 * URL: /recherche/representations
	 * DATA: {"page":&lt;page number&gt;,"per_page":&lt;elements per page&gt;,"filters":{&lt;list of filters&gt;}}<br/>
	 * Available filters:
	 * <ul>
	 * <li>address</li>
	 * <li>city</li>
	 * <li>name</li>
	 * <li>'balcon' seats (seats_balcon)</li>
	 * <li>'fosse' seats (seats_fosse)</li>
	 * <li>'orchestre' seats (seats_orchestre)</li>
	 * </ul>
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
	 * POST Request Body: a JSON object containing a page number "page", the number of elements per page "per_page" and the list of filters in the "filters" array<br/>
	 * URL: /recherche/representations
	 * DATA: {"page":&lt;page number&gt;,"per_page":&lt;elements per page&gt;,"filters":{&lt;list of filters&gt;}}<br/>
	 * Available filters:
	 * <ul>
	 * <li>name</li>
	 * </ul>
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
	 * PUT Request Body: a JSON object containing a page number "page", the number of elements per page "per_page" and the list of filters in the "filters" array<br/>
	 * URL: /recherche/representations
	 * DATA: {"page":&lt;page number&gt;,"per_page":&lt;elements per page&gt;,"filters":{&lt;list of filters&gt;}}<br/>
	 * Available filters:
	 * <ul>
	 * <li>available 'balcon' seats (avail_seats_balcon)</li>
	 * <li>available 'fosse' seats (avail_seats_fosse)</li>
	 * <li>available 'orchestre' seats (avail_seats_orchestre)</li>
	 * <li>date</li>
	 * </ul>
	 * Inherited filters from spectacles:
	 * <ul>
	 * <li>name</li>
	 * <li>genre</li>
	 * <li>city</li>
	 * <li>public</li>
	 * </ul>
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

			search.createFilters(o.getJsonObject("filters"));
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
