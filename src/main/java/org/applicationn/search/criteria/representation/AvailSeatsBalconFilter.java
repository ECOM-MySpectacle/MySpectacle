package org.applicationn.search.criteria.representation;

public class AvailSeatsBalconFilter extends RepresentationFilter
{
	public static final String ID = "seats_balcon";
	private final int seats;

	public AvailSeatsBalconFilter(int seats)
	{
		super(ID);

		this.seats = seats;
	}

	@Override
	public String condition()
	{
		return attribute("nbPlacesBalconLibres") + " >= " + seats;
	}
}
