package org.applicationn.search.criteria.salle;

import org.applicationn.search.criteria.InvalidFilterException;

public class CityFilter extends SalleFilter
{
	public static final String ID = "city";
	private final String city;

	public CityFilter(String city) throws InvalidFilterException
	{
		super(ID);

		if(city == null || city.isEmpty())
		{
			throw new InvalidFilterException(ID);
		}

		this.city = city;
	}

	@Override
	public String condition()
	{
		return attribute("ville") + " = '" + city + "'";
	}
}
