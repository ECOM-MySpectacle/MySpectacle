package org.applicationn.search.criteria.representation;

import org.applicationn.search.criteria.InvalidFilterException;

public class DateFilter extends RepresentationFilter
{
	public static final String ID = "r_date";
	private final String from, to;

	public DateFilter(String from, String to) throws InvalidFilterException
	{
		super(ID);

		if(from == null || to == null || !from.matches("^\\d{4}-\\d{2}-\\d{2}") || !to.matches("^\\d{4}-\\d{2}-\\d{2}"))
		{
			throw new InvalidFilterException(ID);
		}

		this.from = from;
		this.to = to;
	}

	@Override
	public String condition()
	{
		return attribute("date") + " BETWEEN '" + from + "' AND '" + to + "'";
	}
}
