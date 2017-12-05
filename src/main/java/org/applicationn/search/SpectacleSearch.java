package org.applicationn.search;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.util.stream.IntStream;

import org.applicationn.domain.SpectacleEntity;
import org.applicationn.search.criteria.Filter;
import org.applicationn.search.criteria.InvalidFilterException;
import org.applicationn.search.criteria.UnknownFilterException;
import org.applicationn.search.criteria.spectacle.*;
import org.applicationn.service.RechercheService;

public class SpectacleSearch extends Search<SpectacleEntity>
{
	public SpectacleSearch(RechercheService service)
	{
		super(service);
	}

	@Override
	SearchResult<SpectacleEntity> findAll(SearchParameters params)
	{
		return service.findAllSpectacleEntities(params);
	}

	@Override
	SearchResult<SpectacleEntity> findAllMatching(SearchParameters params, String condition)
	{
		return service.findAllSpectacleEntitiesMatching(params, condition);
	}

	@Override
	public Filter createFilter(JsonObject o) throws UnknownFilterException, InvalidFilterException
	{
		String id = o.getString("id");

		switch(id)
		{
			case ArtistFilter.ID:
			{
				return new ArtistFilter(o.getString("artist"));
			}

			case DescFilter.ID:
			{
				return new DescFilter(o.getString("description"));
			}

			case GenreFilter.ID:
			{
				JsonArray a = o.getJsonArray("genre");
				String[] genres = IntStream.range(0, a.size()).mapToObj(a::getString).toArray(String[]::new);

				return new GenreFilter(genres);
			}

			case NameFilter.ID:
			{
				return new NameFilter(o.getString("name"));
			}

			case PublicFilter.ID:
			{
				JsonArray a = o.getJsonArray("public");
				String[] publc = IntStream.range(0, a.size()).mapToObj(a::getString).toArray(String[]::new);

				return new PublicFilter(publc);
			}

			case ThemeFilter.ID:
			{
				return new ThemeFilter(o.getString("theme"));
			}

			default:
			{
				throw new UnknownFilterException(id);
			}
		}
	}
}
