package org.applicationn.search.criteria.salle;

import org.applicationn.search.criteria.InvalidFilterException;

public class SeatsBalconFilter extends SalleFilter
{
	public static final String ID = "sa_seats_balcon";
	private final int seats;

	public SeatsBalconFilter(int seats) throws InvalidFilterException
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
		return attribute("nbPlacesBalcon") + " >= " + seats;
	}
}
