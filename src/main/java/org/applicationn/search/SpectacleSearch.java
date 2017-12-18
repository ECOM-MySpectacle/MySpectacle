package org.applicationn.search;

import javax.json.JsonArray;
import javax.json.JsonValue;
import java.util.stream.IntStream;

import org.applicationn.domain.SpectacleEntity;
import org.applicationn.search.criteria.Filter;
import org.applicationn.search.criteria.spectacle.*;
import org.applicationn.search.exception.InvalidFilterException;
import org.applicationn.search.exception.UnknownFilterException;
import org.applicationn.service.RechercheService;

public class SpectacleSearch extends Search<SpectacleEntity>
{
	public SpectacleSearch(RechercheService service)
	{
		super(service);
	}

	@Override
	SearchResult<SpectacleEntity> find(SearchQuery query)
	{
		return service.findSpectacleEntities(query);
	}

	@Override
	public Filter createFilter(String key, JsonValue value) throws InvalidFilterException, UnknownFilterException
	{
		switch(key)
		{
			case DescFilter.ID:
			{
				return DescFilter.parse(value);
			}

			case GenreFilter.ID:
			{
				JsonArray a = (JsonArray) value;
				String[] genres = IntStream.range(0, a.size()).mapToObj(a::getString).toArray(String[]::new);

				return new GenreFilter(genres);
			}

			case NameFilter.ID:
			{
				return NameFilter.parse(value);
			}

			case PublicFilter.ID:
			{
				JsonArray a = (JsonArray) value;
				String[] publc = IntStream.range(0, a.size()).mapToObj(a::getString).toArray(String[]::new);

				return new PublicFilter(publc);
			}

			case ThemeFilter.ID:
			{
				return ThemeFilter.parse(value);
			}

			default:
			{
				throw new UnknownFilterException(key);
			}
		}
	}
}
