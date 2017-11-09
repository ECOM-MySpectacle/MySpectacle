package org.applicationn.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

import org.applicationn.domain.ArtisteEntity;
import org.applicationn.domain.RepresentationEntity;
import org.applicationn.domain.SalleEntity;
import org.applicationn.domain.SpectacleEntity;
import org.applicationn.service.RechercheService;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

@Path("/recherche")
@Named
public class RechercheResource implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Inject
	private RechercheService rechercheService;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIsNotAllowed()
	{
		return "[]";
	}

	@Path("spectacles")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<SpectacleEntity> findSpectaclesByCriteria(String json)
	{
		// pas d'entrée == pas de critères
		if(json == null || json.isEmpty())
		{
			return rechercheService.findAllSpectacleEntities();
		}

		JSONArray jsonArray = new JSONArray(json);

		// objet json vide == pas de critères
		if(jsonArray.length() == 0)
		{
			return rechercheService.findAllSpectacleEntities();
		}

		// permet de construire la requête
		StringJoiner condition = new StringJoiner(") AND (", "(", ")");

		// pour chaque objet du tableau
		for(int i = 0, len = jsonArray.length(); i < len; i++)
		{
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			// suivant le champ "id" qui correspond au type de critère
			switch(jsonObject.getString("id"))
			{
				// nom
				case "name":
				{
					String name = jsonObject.getString("name");

					condition.add("o.nom = '" + name + "'");

					break;
				}

				// date de création
				case "creation":
				{
					String date = jsonObject.getString("creation");

					condition.add("o.creation = '" + date + "'");

					break;
				}

				// type (théatre, concert, ...)
				case "type":
				{
					String type = jsonObject.getString("type");

					condition.add("o.type = '" + type + "'");

					break;
				}

				case "artist":
				{
					String artiste = jsonObject.getString("artist");

					condition.add("o.artiste = '" + artiste + "'");

					break;
				}

				case "subject":
				{
					String description = jsonObject.getString("subject");

					condition.add("o.sujet LIKE '%" + description + "%'");

					break;
				}

				case "description":
				{
					String description = jsonObject.getString("description");

					condition.add("o.description LIKE '%" + description + "%'");

					break;
				}
			}
		}

		// si aucun critère passé n'a été reconnu,
		return condition.length() == 0 ? Collections.emptyList() : rechercheService.findAllSpectacleEntitiesMatching(condition.toString());
	}

	@Path("salles")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<SalleEntity> findSallesByCriteria(String json)
	{
		// NIY
		return Collections.emptyList();
	}

	@Path("artistes")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<ArtisteEntity> findArtistesByCriteria(String json)
	{
		// NIY
		return Collections.emptyList();
	}

	@Path("representations")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<RepresentationEntity> findRepresentationsByCriteria(String json)
	{
		if(json == null || json.isEmpty())
		{
			return rechercheService.findAllRepresentationEntities();
		}

		JSONArray jsonArray = new JSONArray(json);

		if(jsonArray.length() == 0)
		{
			return rechercheService.findAllRepresentationEntities();
		}

		StringJoiner condition = new StringJoiner(") AND (", "(", ")");

		for(int i = 0, len = jsonArray.length(); i < len; i++)
		{
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			switch(jsonObject.getString("id"))
			{
				// de quand à quand
				case "date":
				{
					String from = jsonObject.getString("from");
					String to = jsonObject.getString("to");

					condition.add("o.date BETWEEN '" + from + "' AND '" + to + "'");

					break;
				}

				case "nbPlacesFosseLibres":
				{
					String nbPlacesFosseLibres = jsonObject.getString("nbPlacesFosseLibres");

					condition.add("o.nbPlacesFosseLibres >= '" + nbPlacesFosseLibres + "'");

					break;
				}

				case "nbPlacesBalconLibres":
				{
					String nbPlacesBalconLibres = jsonObject.getString("nbPlacesBalconLibres");

					condition.add("o.nbPlacesBalconLibres >= '" + nbPlacesBalconLibres + "'");

					break;
				}

				case "nbPlacesOrchestreLibres":
				{
					String nbPlacesOrchestreLibres = jsonObject.getString("nbPlacesOrchestreLibres");

					condition.add("o.nbPlacesOrchestreLibres >= '" + nbPlacesOrchestreLibres + "'");

					break;
				}
			}
		}

		return condition.length() == 0 ? Collections.emptyList() : rechercheService.findAllRepresentationEntitiesMatching(condition.toString());
	}
}
