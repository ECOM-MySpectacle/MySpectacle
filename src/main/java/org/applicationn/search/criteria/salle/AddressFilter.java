package org.applicationn.search.criteria.salle;

import org.applicationn.search.criteria.InvalidFilterException;

public class AddressFilter extends SalleFilter
{
	public static final String ID = "address";
	private final String address;

	public AddressFilter(String address) throws InvalidFilterException
	{
		super(ID);

		if(address == null || address.isEmpty())
		{
			throw new InvalidFilterException(ID);
		}

		this.address = address;
	}

	@Override
	public String condition()
	{
		return attribute("addresse") + " = '" + address + "'";
	}
}
