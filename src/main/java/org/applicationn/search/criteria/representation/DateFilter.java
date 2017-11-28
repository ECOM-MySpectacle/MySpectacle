package org.applicationn.search.criteria.representation;

public class DateFilter extends RepresentationFilter
{
	public static final String ID = "r_date";
	private final String from, to;

	public DateFilter(String from, String to)
	{
		super(ID);

		this.from = from;
		this.to = to;
	}

	@Override
	public String condition()
	{
		return attribute("date") + " BETWEEN '" + from + "' AND '" + to + "'";
	}
}
