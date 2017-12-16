package org.applicationn.search.criteria.salle;

import org.applicationn.search.exception.InvalidFilterException;

public class SeatsBalconFilter extends SalleFilter
{
	public static final String ID = "seats_balcon";

	public SeatsBalconFilter(int seats) throws InvalidFilterException
	{
		super(ID);

		if(seats < 0)
		{
			throw new InvalidFilterException(ID);
		}

		setVar("seats_balcon", seats);
	}

	@Override
	public String condition()
	{
		return attribute("nbPlacesBalcon") + " >= " + variable("seats_balcon");
	}
}
