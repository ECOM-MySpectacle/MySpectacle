package org.applicationn.search;

import javax.json.JsonObject;
import java.util.List;

import org.applicationn.domain.RepresentationEntity;
import org.applicationn.search.criteria.Filter;
import org.applicationn.search.criteria.UnknownFilterException;
import org.applicationn.search.criteria.representation.AvailSeatsBalconFilter;
import org.applicationn.search.criteria.representation.AvailSeatsFosseFilter;
import org.applicationn.search.criteria.representation.AvailSeatsOrchestreFilter;
import org.applicationn.search.criteria.spectacle.NameFilter;
import org.applicationn.service.RechercheService;

public class RepresentationSearch extends Search<RepresentationEntity>
{
	public RepresentationSearch(RechercheService service)
	{
		super(service);
	}

	@Override
	List<RepresentationEntity> findAll()
	{
		return service.findAllRepresentationEntities();
	}

	@Override
	List<RepresentationEntity> findAllMatching(String condition)
	{
		return service.findAllRepresentationEntitiesMatching(condition);
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

			case AvailSeatsBalconFilter.ID:
			{
				return new NameFilter(o.getString("avail_seats_balcon"));
			}

			case AvailSeatsFosseFilter.ID:
			{
				return new NameFilter(o.getString("avail_seats_fosse"));
			}

			case AvailSeatsOrchestreFilter.ID:
			{
				return new NameFilter(o.getString("avail_seats_orchestre"));
			}

			default:
			{
				throw new UnknownFilterException(id);
			}
		}
	}
}
