package org.applicationn.search.criteria.salle;

import org.applicationn.search.criteria.InvalidFilterException;

public class SeatsFosseFilter extends SalleFilter
{
	public static final String ID = "sa_seats_fosse";
	private final int seats;

	public SeatsFosseFilter(int seats) throws InvalidFilterException
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
		return attribute("nbPlacesFosse") + " >= " + seats;
	}
}
