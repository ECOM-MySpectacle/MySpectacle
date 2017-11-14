package org.applicationn.search.criteria.salle;

import org.applicationn.search.criteria.Filter;

public class SeatsOrchestreFilter extends Filter
{
	public static final String ID = "seats_orchestre";
	private final int seats;

	public SeatsOrchestreFilter(int seats)
	{
		super(ID);

		this.seats = seats;
	}

	@Override
	public String condition()
	{
		return "o.nbPlacesOrchestre >= " + seats;
	}
}
