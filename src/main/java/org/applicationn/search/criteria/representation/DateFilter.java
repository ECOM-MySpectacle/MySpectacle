package org.applicationn.search.criteria.representation;

import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.applicationn.search.exception.InvalidFilterException;
import org.applicationn.search.exception.MalformedFilterException;

public class DateFilter extends RepresentationFilter
{
	public static final String ID = "date";

	public DateFilter(String date) throws InvalidFilterException
	{
		this(date, date);
	}

	public DateFilter(String from, String to) throws InvalidFilterException
	{
		super(ID);

		if(from == null || to == null || !from.matches("^\\d{4}-\\d{2}-\\d{2}$") || !to.matches("^\\d{4}-\\d{2}-\\d{2}$"))
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

	public static DateFilter parse(JsonValue json) throws InvalidFilterException, MalformedFilterException
	{
		JsonValue.ValueType type = json.getValueType();

		if(type == JsonValue.ValueType.STRING)
		{
			return new DateFilter(((JsonString) json).getString());
		}

		if(type == JsonValue.ValueType.OBJECT)
		{
			JsonObject o = (JsonObject) json;

			return new DateFilter(o.getString("from"), o.getString("to"));
		}

		throw new MalformedFilterException(ID);
	}

	@Override
	public String condition()
	{
		return attribute("date") + " BETWEEN " + variable("from") + " AND " + variable("to");
	}
}
