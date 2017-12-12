package org.applicationn.search.criteria.representation;

import org.applicationn.search.criteria.InvalidFilterException;

public class AvailSeatsFosseFilter extends RepresentationFilter
{
	public static final String ID = "avail_seats_fosse";
	private final int seats;

	public AvailSeatsFosseFilter(int seats) throws InvalidFilterException
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
		return attribute("nbPlacesFosseLibres") + " >= " + seats;
	}
}
