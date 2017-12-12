package org.applicationn.search.criteria.salle;

import org.applicationn.search.criteria.InvalidFilterException;

public class NameFilter extends SalleFilter
{
	public static final String ID = "name";
	private final String name;

	public NameFilter(String name) throws InvalidFilterException
	{
		super(ID);

		if(name == null || name.isEmpty())
		{
			throw new InvalidFilterException(ID);
		}

		this.name = name;
	}

	@Override
	public String condition()
	{
		return attribute("nom") + " LIKE '%" + name + "%'";
	}
}
