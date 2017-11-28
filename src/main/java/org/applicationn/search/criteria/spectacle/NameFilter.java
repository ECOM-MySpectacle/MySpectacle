package org.applicationn.search.criteria.spectacle;

public class NameFilter extends SpectacleFilter
{
	public static final String ID = "sp_name";
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
