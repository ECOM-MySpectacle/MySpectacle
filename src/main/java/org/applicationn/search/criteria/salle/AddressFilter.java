package org.applicationn.search.criteria.salle;

import org.applicationn.search.exception.InvalidFilterException;

public class AddressFilter extends SalleFilter
{
	public static final String ID = "address";

	public AddressFilter(String address) throws InvalidFilterException
	{
		super(ID);

		if(address == null)
		{
			throw new InvalidFilterException(ID);
		}

		address = address.trim();

		if(address.isEmpty())
		{
			throw new InvalidFilterException(ID);
		}

		setVar("address", "%" + address.toLowerCase() + "%");
	}

	@Override
	public String condition()
	{
		return lower("addresse") + " = " + variable("address");
	}
}
