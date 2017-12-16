package org.applicationn.search.criteria.salle;

import org.applicationn.search.criteria.InvalidFilterException;

public class NameFilter extends SalleFilter
{
	public static final String ID = "name";

	public NameFilter(String name) throws InvalidFilterException
	{
		super(ID);

		if(name == null)
		{
			throw new InvalidFilterException(ID);
		}

		name = name.trim();

		if(name.isEmpty())
		{
			throw new InvalidFilterException(ID);
		}

		setVar("name", "%" + name.toLowerCase() + "%");
	}

	@Override
	public String condition()
	{
		return lower("nom") + " LIKE " + variable("name");
	}
}
