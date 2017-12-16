package org.applicationn.search.criteria.representation;

import org.applicationn.search.exception.InvalidFilterException;

public class AvailSeatsFilter extends RepresentationFilter
{
	public static final String ID = "avail_seats";

	public AvailSeatsFilter(int seats) throws InvalidFilterException
	{
		super(ID);

		if(seats < 0)
		{
			throw new InvalidFilterException(ID);
		}

		setVar("avail_seats", seats);
	}

	@Override
	public String condition()
	{
		return "(" + attribute("nbPlacesBalconLibres") + " + " + attribute("nbPlacesFosseLibres") + " + " + attribute("nbPlacesOrchestreLibres") + ") >= " + variable("avail_seats");
	}
}
