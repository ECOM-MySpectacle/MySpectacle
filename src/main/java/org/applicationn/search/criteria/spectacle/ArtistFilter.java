package org.applicationn.search.criteria.spectacle;

import org.applicationn.search.exception.InvalidFilterException;

public class ArtistFilter extends SpectacleFilter
{
	public static final String ID = "artist";

	public ArtistFilter(String name) throws InvalidFilterException
	{
		super(ID);

		if(name == null)
		{
			throw new InvalidFilterException(ID);
		}

		name = name.trim();

		if(name.isEmpty())
		{
			throw new InvalidFilterException(ID);
		}

		setVar("artist", "%" + name.toLowerCase() + "%");
	}

	@Override
	public String condition()
	{
		return lower("artiste") + " LIKE " + variable("artist");
	}
}
