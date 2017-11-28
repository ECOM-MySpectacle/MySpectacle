package org.applicationn.search.criteria.spectacle;

public class DescFilter extends SpectacleFilter
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
		return attribute("description") + " LIKE '%" + desc + "%'";
	}
}
