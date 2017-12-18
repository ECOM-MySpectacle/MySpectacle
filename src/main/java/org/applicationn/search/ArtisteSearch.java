package org.applicationn.search;

import javax.json.JsonValue;

import org.applicationn.domain.ArtisteEntity;
import org.applicationn.search.criteria.Filter;
import org.applicationn.search.criteria.artiste.NameFilter;
import org.applicationn.search.exception.InvalidFilterException;
import org.applicationn.search.exception.UnknownFilterException;
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
				return NameFilter.parse(value);
			}

			default:
			{
				throw new UnknownFilterException(key);
			}
		}
	}
}
