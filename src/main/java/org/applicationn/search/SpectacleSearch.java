package org.applicationn.search;

import javax.json.JsonValue;

import org.applicationn.domain.SpectacleEntity;
import org.applicationn.search.criteria.Filter;
import org.applicationn.search.criteria.salle.CityFilter;
import org.applicationn.search.criteria.salle.RegionFilter;
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
			case CityFilter.ID:
			{
				return CityFilter.parse(value);
			}

			case DescFilter.ID:
			{
				return DescFilter.parse(value);
			}

			case GenreFilter.ID:
			{
				return GenreFilter.parse(value);
			}

			case NameFilter.ID:
			{
				return NameFilter.parse(value);
			}

			case PublicFilter.ID:
			{
				return PublicFilter.parse(value);
			}

			case RegionFilter.ID:
			{
				return RegionFilter.parse(value);
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
