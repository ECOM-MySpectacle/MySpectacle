package org.applicationn.search.criteria.salle;

public class SeatsBalconFilter extends SalleFilter
{
	public static final String ID = "sa_seats_balcon";
	private final int seats;

	public SeatsBalconFilter(int seats)
	{
		super(ID);

		this.seats = seats;
	}

	@Override
	public String condition()
	{
		return attribute("nbPlacesBalcon") + " >= " + seats;
	}
}
