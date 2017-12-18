package org.applicationn.search.criteria.spectacle;

import javax.json.JsonString;
import javax.json.JsonValue;

import org.applicationn.search.exception.InvalidFilterException;

public class ThemeFilter extends SpectacleFilter
{
	public static final String ID = "theme";

	private ThemeFilter(String theme)
	{
		super(ID);

		setVar("theme", "%" + theme.toLowerCase() + "%");
	}

	@Override
	public String condition()
	{
		return lower("theme") + " LIKE " + variable("theme");
	}

	public static ThemeFilter parse(JsonValue json) throws InvalidFilterException
	{
		String value = ((JsonString) json).getString();

		if(value == null)
		{
			throw new InvalidFilterException(ID);
		}

		return value.trim().isEmpty() ? null : new ThemeFilter(value);
	}
}
