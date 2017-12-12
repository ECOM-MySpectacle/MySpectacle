package org.applicationn.search;

import javax.json.*;
import java.util.stream.IntStream;

import org.applicationn.domain.RepresentationEntity;
import org.applicationn.search.criteria.Filter;
import org.applicationn.search.criteria.InvalidFilterException;
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
	Filter createFilter(String key, JsonValue value) throws InvalidFilterException
	{
		switch(key)
		{
			case NameFilter.ID:
			{
				return new NameFilter(((JsonString) value).getString());
			}

			case GenreFilter.ID:
			{
				JsonArray a = ((JsonArray) value);
				String[] genres = IntStream.range(0, a.size()).mapToObj(a::getString).toArray(String[]::new);

				return new GenreFilter(genres);
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
