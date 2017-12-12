package org.applicationn.search;

import javax.json.JsonString;
import javax.json.JsonValue;

import org.applicationn.domain.ArtisteEntity;
import org.applicationn.search.criteria.Filter;
import org.applicationn.search.criteria.InvalidFilterException;
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
	Filter createFilter(String key, JsonValue value) throws InvalidFilterException
	{
		switch(key)
		{
			case NameFilter.ID:
			{
				return new NameFilter(((JsonString) value).getString());
			}

			default:
			{
				return null;
			}
		}
	}
}
