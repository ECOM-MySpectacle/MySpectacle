package org.applicationn.search.criteria.representation;

public class AvailSeatsFosseFilter extends RepresentationFilter
{
	public static final String ID = "seats_fosse";
	private final int seats;

	public AvailSeatsFosseFilter(int seats)
	{
		super(ID);

		this.seats = seats;
	}

	@Override
	public String condition()
	{
		return attribute("nbPlacesFosseLibres") + " >= " + seats;
	}
}
