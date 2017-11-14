package org.applicationn.search.criteria.salle;

import org.applicationn.search.criteria.Filter;

public class SeatsBalconFilter extends Filter
{
	public static final String ID = "seats_balcon";
	private final int seats;

	public SeatsBalconFilter(int seats)
	{
		super(ID);

		this.seats = seats;
	}

	@Override
	public String condition()
	{
		return "o.nbPlacesBalcon >= " + seats;
	}
}
