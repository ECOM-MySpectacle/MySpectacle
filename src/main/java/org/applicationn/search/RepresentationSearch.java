package org.applicationn.search;

import javax.json.*;
import java.util.stream.IntStream;

import org.applicationn.domain.RepresentationEntity;
import org.applicationn.search.criteria.Filter;
import org.applicationn.search.criteria.InvalidFilterException;
import org.applicationn.search.criteria.MalformedFilterException;
import org.applicationn.search.criteria.representation.*;
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
	SearchResult<RepresentationEntity> findAll(SearchQuery query)
	{
		return service.findAllRepresentationEntities(query);
	}

	@Override
	SearchResult<RepresentationEntity> findAllMatching(SearchQuery query)
	{
		return service.findAllRepresentationEntitiesMatching(query);
	}

	@Override
	Filter createFilter(String key, JsonValue value) throws InvalidFilterException, MalformedFilterException
	{
		switch(key)
		{
			case NameFilter.ID:
			{
				return new NameFilter(((JsonString) value).getString());
			}

			case GenreFilter.ID:
			{
				return GenreFilter.parse(value);
			}

			case DateFilter.ID:
			{
				JsonObject o = ((JsonObject) value);

				return new DateFilter(o.getString("from"), o.getString("to"));
			}

			case CityFilter.ID:
			{
				return new CityFilter(((JsonString) value).getString());
			}

			case PublicFilter.ID:
			{
				JsonArray a = ((JsonArray) value);
				String[] publc = IntStream.range(0, a.size()).mapToObj(a::getString).toArray(String[]::new);

				return new PublicFilter(publc);
			}

			case AvailSeatsFilter.ID:
			{
				return new AvailSeatsFilter(((JsonNumber) value).intValue());
			}

			case AvailSeatsBalconFilter.ID:
			{
				return new AvailSeatsBalconFilter(((JsonNumber) value).intValue());
			}

			case AvailSeatsFosseFilter.ID:
			{
				return new AvailSeatsFosseFilter(((JsonNumber) value).intValue());
			}

			case AvailSeatsOrchestreFilter.ID:
			{
				return new AvailSeatsOrchestreFilter(((JsonNumber) value).intValue());
			}

			default:
			{
				return null;
			}
		}
	}
}
