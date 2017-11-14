package org.applicationn.search.criteria.salle;

import org.applicationn.search.criteria.Filter;

public class AddressFilter extends Filter
{
	public static final String ID = "address";
	private final String address;

	public AddressFilter(String address)
	{
		super(ID);

		this.address = address;
	}

	@Override
	public String condition()
	{
		return "o.addresse = '" + address + "'";
	}
}
