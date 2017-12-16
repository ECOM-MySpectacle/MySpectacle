package org.applicationn.search.criteria.spectacle;

import org.applicationn.search.exception.InvalidFilterException;

public class DescFilter extends SpectacleFilter
{
	public static final String ID = "description";

	public DescFilter(String desc) throws InvalidFilterException
	{
		super(ID);

		if(desc == null)
		{
			throw new InvalidFilterException(ID);
		}

		desc = desc.trim();

		if(desc.isEmpty())
		{
			throw new InvalidFilterException(ID);
		}

		setVar("desc", "%" + desc.toLowerCase() + "%");
	}

	@Override
	public String condition()
	{
		return lower("description") + " LIKE " + variable("desc");
	}
}
