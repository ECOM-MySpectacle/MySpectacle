package org.applicationn.search.criteria.spectacle;

import java.util.Arrays;
import java.util.StringJoiner;

import org.applicationn.domain.SpectacleGenre;
import org.applicationn.search.criteria.InvalidFilterException;

public class GenreFilter extends SpectacleFilter
{
	public static final String ID = "sp_genre";
	private final String[] genres;

	public GenreFilter(String[] genres) throws InvalidFilterException
	{
		super(ID);

		if(genres == null || genres.length == 0)
		{
			throw new InvalidFilterException(ID);
		}

		for(String genre : genres)
		{
			if(Arrays.stream(SpectacleGenre.values()).map(Enum::toString).noneMatch(s -> s.equalsIgnoreCase(genre)))
			{
				throw new InvalidFilterException(ID);
			}
		}

		this.genres = genres;
	}

	@Override
	public String condition()
	{
		StringJoiner joiner = new StringJoiner("','", "'", "'");
		Arrays.stream(genres).forEach(joiner::add);
		return attribute("genre") + " IN (" + joiner.toString() + ")";
	}
}
