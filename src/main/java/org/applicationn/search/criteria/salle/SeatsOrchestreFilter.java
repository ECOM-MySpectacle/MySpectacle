package org.applicationn.search.criteria.salle;

import org.applicationn.search.criteria.InvalidFilterException;

public class SeatsOrchestreFilter extends SalleFilter
{
	public static final String ID = "seats_orchestre";
	private final int seats;

	public SeatsOrchestreFilter(int seats) throws InvalidFilterException
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
		return attribute("nbPlacesOrchestre") + " >= " + seats;
	}
}
