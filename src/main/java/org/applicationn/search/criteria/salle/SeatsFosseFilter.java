package org.applicationn.search.criteria.salle;

public class SeatsFosseFilter extends SalleFilter
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
		return attribute("nbPlacesFosse") + " >= " + seats;
	}
}
