package org.applicationn.search.criteria.salle;

import javax.json.JsonString;
import javax.json.JsonValue;

import org.applicationn.search.exception.InvalidFilterException;

public class AddressFilter extends SalleFilter
{
	public static final String ID = "address";

	private AddressFilter(String address)
	{
		super(ID);

		setVar("address", address.toLowerCase());
	}

	@Override
	public String condition()
	{
		return lower("addresse") + " = " + variable("address");
	}

	public static AddressFilter parse(JsonValue json) throws InvalidFilterException
	{
		String value = ((JsonString) json).getString();

		if(value == null)
		{
			throw new InvalidFilterException(ID);
		}

		return value.trim().isEmpty() ? null : new AddressFilter(value);
	}
}
