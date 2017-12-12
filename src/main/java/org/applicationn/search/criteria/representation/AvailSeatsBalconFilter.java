package org.applicationn.search.criteria.representation;

import org.applicationn.search.criteria.InvalidFilterException;

public class AvailSeatsBalconFilter extends RepresentationFilter
{
	public static final String ID = "avail_seats_balcon";
	private final int seats;

	public AvailSeatsBalconFilter(int seats) throws InvalidFilterException
	{
		super(ID);

		if(seats < 0)
		{
			throw new InvalidFilterException(ID);
		}

		this.seats = seats;
	}

	@Override
	public String condition()
	{
		return attribute("nbPlacesBalconLibres") + " >= " + seats;
	}
}
