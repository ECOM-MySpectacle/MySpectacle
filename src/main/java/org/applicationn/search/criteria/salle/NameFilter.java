package org.applicationn.search.criteria.salle;

public class NameFilter extends SalleFilter
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
		return attribute("nom") + " LIKE '%" + name + "%'";
	}
}
