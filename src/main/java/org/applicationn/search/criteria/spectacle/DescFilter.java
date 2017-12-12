package org.applicationn.search.criteria.spectacle;

import org.applicationn.search.criteria.InvalidFilterException;

public class DescFilter extends SpectacleFilter
{
	public static final String ID = "description";
	private final String desc;

	public DescFilter(String desc) throws InvalidFilterException
	{
		super(ID);

		if(desc == null || desc.isEmpty())
		{
			throw new InvalidFilterException(ID);
		}

		this.desc = desc;
	}

	@Override
	public String condition()
	{
		return attribute("description") + " LIKE '%" + desc + "%'";
	}
}
