package org.applicationn.search.criteria.salle;

import org.applicationn.search.criteria.Filter;

public class SeatsFosseFilter extends Filter
{
	public static final String ID = "seats_fosse";
	private final int seats;

	public SeatsFosseFilter(int seats)
	{
		super(ID);

		this.seats = seats;
	}

	@Override
	public String condition()
	{
		return "o.nbPlacesFosse >= " + seats;
	}
}
