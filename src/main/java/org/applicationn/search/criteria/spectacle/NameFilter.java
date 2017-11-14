package org.applicationn.search.criteria.spectacle;

import org.applicationn.search.criteria.Filter;

public class NameFilter extends Filter
{
	public static final String ID = "name";
	private final String name;

	public NameFilter(String name)
	{
		super(ID);

		this.name = name;
	}

	@Override
	public String condition()
	{
		return "o.nom LIKE '%" + name + "%'";
	}
}
