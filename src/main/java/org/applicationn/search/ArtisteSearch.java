package org.applicationn.search;

import javax.json.JsonString;
import javax.json.JsonValue;

import org.applicationn.domain.ArtisteEntity;
import org.applicationn.search.criteria.Filter;
import org.applicationn.search.exception.InvalidFilterException;
import org.applicationn.search.exception.UnknownFilterException;
import org.applicationn.search.criteria.artiste.NameFilter;
import org.applicationn.service.RechercheService;

public class ArtisteSearch extends Search<ArtisteEntity>
{
	public ArtisteSearch(RechercheService service)
	{
		super(service);
	}

	@Override
	SearchResult<ArtisteEntity> find(SearchQuery query)
	{
		return service.findArtisteEntities(query);
	}

	@Override
	Filter createFilter(String key, JsonValue value) throws InvalidFilterException, UnknownFilterException
	{
		switch(key)
		{
			case NameFilter.ID:
			{
				return new NameFilter(((JsonString) value).getString());
			}

			default:
			{
				throw new UnknownFilterException(key);
			}
		}
	}
}
