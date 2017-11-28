package org.applicationn.search;

import javax.json.JsonArray;
import javax.json.JsonObject;

import java.util.stream.IntStream;

import org.applicationn.domain.RepresentationEntity;
import org.applicationn.search.criteria.Filter;
import org.applicationn.search.criteria.UnknownFilterException;
import org.applicationn.search.criteria.representation.AvailSeatsBalconFilter;
import org.applicationn.search.criteria.representation.AvailSeatsFosseFilter;
import org.applicationn.search.criteria.representation.AvailSeatsOrchestreFilter;
import org.applicationn.search.criteria.representation.DateFilter;
import org.applicationn.search.criteria.salle.CityFilter;
import org.applicationn.search.criteria.spectacle.GenreFilter;
import org.applicationn.search.criteria.spectacle.NameFilter;
import org.applicationn.search.criteria.spectacle.PublicFilter;
import org.applicationn.service.RechercheService;

public class RepresentationSearch extends Search<RepresentationEntity>
{
	public RepresentationSearch(RechercheService service)
	{
		super(service);
	}

	@Override
	SearchResult<RepresentationEntity> findAll(SearchParameters params)
	{
		return service.findAllRepresentationEntities(params);
	}

	@Override
	SearchResult<RepresentationEntity> findAllMatching(SearchParameters params, String condition)
	{
		return service.findAllRepresentationEntitiesMatching(params, condition);
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

			case GenreFilter.ID:
			{
				JsonArray a = o.getJsonArray("genre");
				String[] genres = IntStream.range(0, a.size()).mapToObj(a::getString).toArray(String[]::new);

				return new GenreFilter(genres);
			}

			case DateFilter.ID:
			{
				return new DateFilter(o.getString("from"), o.getString("to"));
			}

			case CityFilter.ID:
			{
				return new CityFilter(o.getString("city"));
			}

			case PublicFilter.ID:
			{
				JsonArray a = o.getJsonArray("public");
				String[] publc = IntStream.range(0, a.size()).mapToObj(a::getString).toArray(String[]::new);

				return new PublicFilter(publc);
			}

			case AvailSeatsBalconFilter.ID:
			{
				return new AvailSeatsBalconFilter(o.getInt("avail_seats_balcon"));
			}

			case AvailSeatsFosseFilter.ID:
			{
				return new AvailSeatsFosseFilter(o.getInt("avail_seats_fosse"));
			}

			case AvailSeatsOrchestreFilter.ID:
			{
				return new AvailSeatsOrchestreFilter(o.getInt("avail_seats_orchestre"));
			}

			default:
			{
				throw new UnknownFilterException(id);
			}
		}
	}
}
