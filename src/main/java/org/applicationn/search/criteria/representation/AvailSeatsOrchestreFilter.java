package org.applicationn.search.criteria.representation;

import org.applicationn.search.criteria.InvalidFilterException;

public class AvailSeatsOrchestreFilter extends RepresentationFilter
{
	public static final String ID = "avail_seats_orchestre";
	private final int seats;

	public AvailSeatsOrchestreFilter(int seats) throws InvalidFilterException
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
		return attribute("nbPlacesOrchestreLibres") + " >= " + seats;
	}
}
