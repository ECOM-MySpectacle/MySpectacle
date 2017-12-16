package org.applicationn.search;

import javax.json.JsonArray;
import javax.json.JsonString;
import javax.json.JsonValue;
import java.util.stream.IntStream;

import org.applicationn.domain.SpectacleEntity;
import org.applicationn.search.criteria.Filter;
import org.applicationn.search.criteria.InvalidFilterException;
import org.applicationn.search.criteria.spectacle.*;
import org.applicationn.service.RechercheService;

public class SpectacleSearch extends Search<SpectacleEntity>
{
	public SpectacleSearch(RechercheService service)
	{
		super(service);
	}

	@Override
	SearchResult<SpectacleEntity> findAll(SearchQuery query)
	{
		return service.findAllSpectacleEntities(query);
	}

	@Override
	SearchResult<SpectacleEntity> findAllMatching(SearchQuery query)
	{
		return service.findAllSpectacleEntitiesMatching(query);
	}

	@Override
	public Filter createFilter(String key, JsonValue value) throws InvalidFilterException
	{
		switch(key)
		{
			case ArtistFilter.ID:
			{
				return new ArtistFilter(((JsonString) value).getString());
			}

			case DescFilter.ID:
			{
				return new DescFilter(((JsonString) value).getString());
			}

			case GenreFilter.ID:
			{
				JsonArray a = ((JsonArray) value);
				String[] genres = IntStream.range(0, a.size()).mapToObj(a::getString).toArray(String[]::new);

				return new GenreFilter(genres);
			}

			case NameFilter.ID:
			{
				return new NameFilter(((JsonString) value).getString());
			}

			case PublicFilter.ID:
			{
				JsonArray a = ((JsonArray) value);
				String[] publc = IntStream.range(0, a.size()).mapToObj(a::getString).toArray(String[]::new);

				return new PublicFilter(publc);
			}

			case ThemeFilter.ID:
			{
				return new ThemeFilter(((JsonString) value).getString());
			}

			default:
			{
				return null;
			}
		}
	}
}
