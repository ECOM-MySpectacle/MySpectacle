package org.applicationn.search.criteria.salle;

import javax.json.JsonString;
import javax.json.JsonValue;

import org.applicationn.search.exception.InvalidFilterException;

@Deprecated
public class CityFilter extends SalleFilter
{
	public static final String ID = "city";

	private CityFilter(String city)
	{
		super(ID);

		setVar("city", "%" + city.toLowerCase() + "%");
	}

	@Override
	public String condition()
	{
		return lower("ville") + " = " + variable("city");
	}

	@Deprecated
	public static CityFilter parse(JsonValue json) throws InvalidFilterException
	{
		String value = ((JsonString) json).getString();

		if(value == null)
		{
			throw new InvalidFilterException(ID);
		}

		return value.trim().isEmpty() ? null : new CityFilter(value);
	}
}
