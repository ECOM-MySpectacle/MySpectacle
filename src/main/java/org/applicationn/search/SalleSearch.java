package org.applicationn.search;

import javax.json.JsonNumber;
import javax.json.JsonValue;

import org.applicationn.domain.SalleEntity;
import org.applicationn.search.criteria.Filter;
import org.applicationn.search.criteria.salle.*;
import org.applicationn.search.exception.InvalidFilterException;
import org.applicationn.search.exception.UnknownFilterException;
import org.applicationn.service.RechercheService;

public class SalleSearch extends Search<SalleEntity>
{
	public SalleSearch(RechercheService service)
	{
		super(service);
	}

	@Override
	SearchResult<SalleEntity> find(SearchQuery query)
	{
		return service.findSalleEntities(query);
	}

	@Override
	Filter createFilter(String key, JsonValue value) throws InvalidFilterException, UnknownFilterException
	{
		switch(key)
		{
			case AddressFilter.ID:
			{
				return AddressFilter.parse(value);
			}

			case CityFilter.ID:
			{
				return CityFilter.parse(value);
			}

			case NameFilter.ID:
			{
				return NameFilter.parse(value);
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
				throw new UnknownFilterException(key);
			}
		}
	}
}
