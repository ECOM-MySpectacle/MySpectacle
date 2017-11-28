package org.applicationn.search;

import javax.json.JsonObject;

import org.applicationn.domain.ArtisteEntity;
import org.applicationn.search.criteria.Filter;
import org.applicationn.search.criteria.UnknownFilterException;
import org.applicationn.search.criteria.artiste.NameFilter;
import org.applicationn.service.RechercheService;

public class ArtisteSearch extends Search<ArtisteEntity>
{
	public ArtisteSearch(RechercheService service)
	{
		super(service);
	}

	@Override
	SearchResult<ArtisteEntity> findAll(SearchParameters params)
	{
		return service.findAllArtisteEntities(params);
	}

	@Override
	SearchResult<ArtisteEntity> findAllMatching(SearchParameters params, String condition)
	{
		return service.findAllArtisteEntitiesMatching(params, condition);
	}

	@Override
	Filter createFilter(JsonObject o) throws UnknownFilterException
	{
		String id = o.getString("id");

		switch(id)
		{
			case NameFilter.ID:
			{
				return new NameFilter(o.getString("name"));
			}

			default:
			{
				throw new UnknownFilterException(id);
			}
		}
	}
}
