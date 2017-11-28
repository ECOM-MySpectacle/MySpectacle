package org.applicationn.search.criteria.representation;

public class AvailSeatsOrchestreFilter extends RepresentationFilter
{
	public static final String ID = "r_avail_seats_orchestre";
	private final int seats;

	public AvailSeatsOrchestreFilter(int seats)
	{
		super(ID);

		this.seats = seats;
	}

	@Override
	public String condition()
	{
		return attribute("nbPlacesOrchestreLibres") + " >= " + seats;
	}
}
