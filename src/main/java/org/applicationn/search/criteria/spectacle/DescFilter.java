package org.applicationn.search.criteria.spectacle;

import javax.json.JsonString;
import javax.json.JsonValue;

import org.applicationn.search.exception.InvalidFilterException;

public class DescFilter extends SpectacleFilter
{
	public static final String ID = "description";

	private DescFilter(String desc)
	{
		super(ID);

		setVar("desc", "%" + desc.toLowerCase() + "%");
	}

	@Override
	public String condition()
	{
		return lower("description") + " LIKE " + variable("desc");
	}

	public static DescFilter parse(JsonValue json) throws InvalidFilterException
	{
		String value = ((JsonString) json).getString();

		if(value == null)
		{
			throw new InvalidFilterException(ID);
		}

		return value.trim().isEmpty() ? null : new DescFilter(value);
	}
}
