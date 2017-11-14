package org.applicationn.search.criteria.spectacle;

import org.applicationn.search.criteria.Filter;

public class DescFilter extends Filter
{
	public static final String ID = "description";
	private final String desc;

	public DescFilter(String desc)
	{
		super(ID);

		this.desc = desc;
	}

	@Override
	public String condition()
	{
		return "o.description LIKE '%" + desc + "%'";
	}
}
