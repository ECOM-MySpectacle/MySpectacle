package org.applicationn.search.criteria.salle;

public class SeatsOrchestreFilter extends SalleFilter
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
		return attribute("nbPlacesOrchestre") + " >= " + seats;
	}
}
