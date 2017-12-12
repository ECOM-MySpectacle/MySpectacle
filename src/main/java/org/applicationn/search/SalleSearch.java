package org.applicationn.search;

import javax.json.JsonNumber;
import javax.json.JsonString;
import javax.json.JsonValue;

import org.applicationn.domain.SalleEntity;
import org.applicationn.search.criteria.Filter;
import org.applicationn.search.criteria.InvalidFilterException;
import org.applicationn.search.criteria.salle.*;
import org.applicationn.service.RechercheService;

public class SalleSearch extends Search<SalleEntity>
{
	public SalleSearch(RechercheService service)
	{
		super(service);
	}

	@Override
	SearchResult<SalleEntity> findAll(SearchParameters params)
	{
		return service.findAllSalleEntities(params);
	}

	@Override
	SearchResult<SalleEntity> findAllMatching(SearchParameters params, String condition)
	{
		return service.findAllSalleEntitiesMatching(params, condition);
	}

	@Override
	Filter createFilter(String key, JsonValue value) throws InvalidFilterException
	{
		switch(key)
		{
			case AddressFilter.ID:
			{
				return new AddressFilter(((JsonString) value).getString());
			}

			case CityFilter.ID:
			{
				return new CityFilter(((JsonString) value).getString());
			}

			case NameFilter.ID:
			{
				return new NameFilter(((JsonString) value).getString());
			}

			case SeatsBalconFilter.ID:
			{
				return new SeatsBalconFilter(((JsonNumber) value).intValue());
			}

			case SeatsFosseFilter.ID:
			{
				return new SeatsFosseFilter(((JsonNumber) value).intValue());
			}

			case SeatsOrchestreFilter.ID:
			{
				return new SeatsOrchestreFilter(((JsonNumber) value).intValue());
			}

			default:
			{
				return null;
			}
		}
	}
}
