package org.applicationn.search.criteria.representation;

import org.applicationn.search.criteria.InvalidFilterException;

public class AvailSeatsOrchestreFilter extends RepresentationFilter
{
	public static final String ID = "avail_seats_orchestre";

	public AvailSeatsOrchestreFilter(int seats) throws InvalidFilterException
	{
		super(ID);

		if(seats < 0)
		{
			throw new InvalidFilterException(ID);
		}

		setVar("avail_seats_orchestre", seats);
	}

	@Override
	public String condition()
	{
		return attribute("nbPlacesOrchestreLibres") + " >= " + variable("avail_seats_orchestre");
	}
}
