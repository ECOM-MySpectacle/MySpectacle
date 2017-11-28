package org.applicationn.search.criteria.salle;

public class CityFilter extends SalleFilter
{
	public static final String ID = "sa_city";
	private final String city;

	public CityFilter(String city)
	{
		super(ID);

		this.city = city;
	}

	@Override
	public String condition()
	{
		return attribute("ville") + " = '" + city + "'";
	}
}
