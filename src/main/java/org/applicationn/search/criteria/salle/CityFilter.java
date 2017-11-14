package org.applicationn.search.criteria.salle;

import org.applicationn.search.criteria.Filter;

public class CityFilter extends Filter
{
	public static final String ID = "city";
	private final String city;

	public CityFilter(String city)
	{
		super(ID);

		this.city = city;
	}

	@Override
	public String condition()
	{
		return "o.ville = '" + city + "'";
	}
}
