package org.applicationn.search.criteria.representation;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.applicationn.search.criteria.InvalidFilterException;

public class DateFilter extends RepresentationFilter
{
	public static final String ID = "date";

	public DateFilter(String from, String to) throws InvalidFilterException
	{
		super(ID);

		if(from == null || to == null || !from.matches("^\\d{4}-\\d{2}-\\d{2}") || !to.matches("^\\d{4}-\\d{2}-\\d{2}"))
		{
			throw new InvalidFilterException(ID);
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		try
		{
			setVar("from", df.parse(from));
			setVar("to", df.parse(to));
		}
		catch(ParseException e)
		{
			throw new InvalidFilterException(ID);
		}
	}

	@Override
	public String condition()
	{
		return attribute("date") + " BETWEEN " + variable("from") + " AND " + variable("to");
	}
}
