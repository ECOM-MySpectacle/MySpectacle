package org.applicationn.search.criteria.salle;

public class AddressFilter extends SalleFilter
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
		return attribute("addresse") + " = '" + address + "'";
	}
}
