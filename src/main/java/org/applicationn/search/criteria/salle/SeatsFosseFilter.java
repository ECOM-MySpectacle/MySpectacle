package org.applicationn.search.criteria.salle;

import org.applicationn.search.exception.InvalidFilterException;

public class SeatsFosseFilter extends SalleFilter
{
	public static final String ID = "seats_fosse";

	public SeatsFosseFilter(int seats) throws InvalidFilterException
	{
		super(ID);

		if(seats < 0)
		{
			throw new InvalidFilterException(ID);
		}

		setVar("seats_fosse", seats);
	}

	@Override
	public String condition()
	{
		return attribute("nbPlacesFosse") + " >= " + variable("seats_fosse");
	}
}
