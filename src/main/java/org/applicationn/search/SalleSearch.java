package org.applicationn.search;

import javax.json.JsonObject;

import org.applicationn.domain.SalleEntity;
import org.applicationn.search.criteria.Filter;
import org.applicationn.search.criteria.UnknownFilterException;
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
	Filter createFilter(JsonObject o) throws UnknownFilterException
	{
		String id = o.getString("id");

		switch(id)
		{
			case AddressFilter.ID:
			{
				return new AddressFilter(o.getString("address"));
			}

			case CityFilter.ID:
			{
				return new CityFilter(o.getString("city"));
			}

			case NameFilter.ID:
			{
				return new NameFilter(o.getString("name"));
			}

			case SeatsBalconFilter.ID:
			{
				return new SeatsBalconFilter(o.getInt("seats_balcon"));
			}

			case SeatsFosseFilter.ID:
			{
				return new SeatsFosseFilter(o.getInt("seats_fosse"));
			}

			case SeatsOrchestreFilter.ID:
			{
				return new SeatsOrchestreFilter(o.getInt("seats_orchestre"));
			}

			default:
			{
				throw new UnknownFilterException(id);
			}
		}
	}
}
