package org.applicationn.search.criteria.representation;

import org.applicationn.search.exception.InvalidFilterException;

public class AvailSeatsBalconFilter extends RepresentationFilter
{
	public static final String ID = "avail_seats_balcon";

	public AvailSeatsBalconFilter(int seats) throws InvalidFilterException
	{
		super(ID);

		if(seats < 0)
		{
			throw new InvalidFilterException(ID);
		}

		setVar("avail_seats_balcon", seats);
	}

	@Override
	public String condition()
	{
		return attribute("nbPlacesBalconLibres") + " >= " + variable("avail_seats_balcon");
	}
}
