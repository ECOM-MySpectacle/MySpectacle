package org.applicationn.search.criteria.salle;

import org.applicationn.search.exception.InvalidFilterException;

public class CityFilter extends SalleFilter
{
	public static final String ID = "city";

	public CityFilter(String city) throws InvalidFilterException
	{
		super(ID);

		if(city == null)
		{
			throw new InvalidFilterException(ID);
		}

		city = city.trim();

		if(city.isEmpty())
		{
			throw new InvalidFilterException(ID);
		}

		setVar("city", "%" + city.toLowerCase() + "%");
	}

	@Override
	public String condition()
	{
		return lower("ville") + " = " + variable("city");
	}
}
