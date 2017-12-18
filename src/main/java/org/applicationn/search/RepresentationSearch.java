package org.applicationn.search;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonValue;
import java.util.stream.IntStream;

import org.applicationn.domain.RepresentationEntity;
import org.applicationn.search.criteria.Filter;
import org.applicationn.search.criteria.representation.*;
import org.applicationn.search.criteria.salle.CityFilter;
import org.applicationn.search.criteria.spectacle.GenreFilter;
import org.applicationn.search.criteria.spectacle.NameFilter;
import org.applicationn.search.criteria.spectacle.PublicFilter;
import org.applicationn.search.exception.InvalidFilterException;
import org.applicationn.search.exception.MalformedFilterException;
import org.applicationn.search.exception.UnknownFilterException;
import org.applicationn.service.RechercheService;

public class RepresentationSearch extends Search<RepresentationEntity>
{
	public RepresentationSearch(RechercheService service)
	{
		super(service);
	}

	@Override
	SearchResult<RepresentationEntity> find(SearchQuery query)
	{
		return service.findRepresentationEntities(query);
	}

	@Override
	Filter createFilter(String key, JsonValue value) throws InvalidFilterException, MalformedFilterException, UnknownFilterException
	{
		switch(key)
		{
			case NameFilter.ID:
			{
				return NameFilter.parse(value);
			}

			case GenreFilter.ID:
			{
				JsonArray a = (JsonArray) value;
				String[] genres = IntStream.range(0, a.size()).mapToObj(a::getString).toArray(String[]::new);

				return new GenreFilter(genres);
			}

			case DateFilter.ID:
			{
				return DateFilter.parse(value);
			}

			case CityFilter.ID:
			{
				return CityFilter.parse(value);
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
				throw new UnknownFilterException(key);
			}
		}
	}
}
