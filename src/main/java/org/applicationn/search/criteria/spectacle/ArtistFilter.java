package org.applicationn.search.criteria.spectacle;

import javax.json.JsonString;
import javax.json.JsonValue;

import org.applicationn.search.exception.InvalidFilterException;

public class ArtistFilter extends SpectacleFilter
{
	public static final String ID = "artist";

	private ArtistFilter(String name)
	{
		super(ID);

		setVar("artist", "%" + name.toLowerCase() + "%");
	}

	@Override
	public String condition()
	{
		return lower("artiste") + " LIKE " + variable("artist");
	}

	public static ArtistFilter parse(JsonValue json) throws InvalidFilterException
	{
		String value = ((JsonString) json).getString();

		if(value == null)
		{
			throw new InvalidFilterException(ID);
		}

		return value.trim().isEmpty() ? null : new ArtistFilter(value);
	}
}
