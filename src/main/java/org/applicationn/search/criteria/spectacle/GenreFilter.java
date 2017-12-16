package org.applicationn.search.criteria.spectacle;

import java.util.Arrays;
import java.util.EnumSet;

import org.applicationn.domain.SpectacleGenre;
import org.applicationn.search.criteria.InvalidFilterException;

public class GenreFilter extends SpectacleFilter
{
	public static final String ID = "genre";

	public GenreFilter(String[] genres) throws InvalidFilterException
	{
		super(ID);

		if(genres == null || genres.length == 0)
		{
			throw new InvalidFilterException(ID);
		}

		EnumSet<SpectacleGenre> e = EnumSet.noneOf(SpectacleGenre.class);

		for(String genre : genres)
		{
			SpectacleGenre sg = Arrays.stream(SpectacleGenre.values()).filter(s -> s.toString().equalsIgnoreCase(genre)).findFirst().orElse(null);

			if(sg == null)
			{
				throw new InvalidFilterException(ID);
			}

			e.add(sg);
		}

		setVar("genres", e);
	}

	@Override
	public String condition()
	{
		return attribute("genre") + " IN (" + variable("genres") + ")";
	}
}
