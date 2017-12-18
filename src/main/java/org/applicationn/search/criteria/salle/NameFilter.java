package org.applicationn.search.criteria.salle;

import javax.json.JsonString;
import javax.json.JsonValue;

import org.applicationn.search.exception.InvalidFilterException;

public class NameFilter extends SalleFilter
{
	public static final String ID = "name";

	private NameFilter(String name)
	{
		super(ID);

		setVar("name", "%" + name.toLowerCase() + "%");
	}

	@Override
	public String condition()
	{
		return lower("nom") + " LIKE " + variable("name");
	}

	public static NameFilter parse(JsonValue json) throws InvalidFilterException
	{
		String value = ((JsonString) json).getString();

		if(value == null)
		{
			throw new InvalidFilterException(ID);
		}

		return value.trim().isEmpty() ? null : new NameFilter(value);
	}
}
