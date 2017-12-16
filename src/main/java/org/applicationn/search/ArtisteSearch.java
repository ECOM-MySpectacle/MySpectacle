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
	SearchResult<ArtisteEntity> findAll(SearchQuery query)
	{
		return service.findAllArtisteEntities(query);
	}

	@Override
	SearchResult<ArtisteEntity> findAllMatching(SearchQuery query)
	{
		return service.findAllArtisteEntitiesMatching(query);
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
