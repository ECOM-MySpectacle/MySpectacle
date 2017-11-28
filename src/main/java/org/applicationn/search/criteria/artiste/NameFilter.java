package org.applicationn.search.criteria.artiste;

public class NameFilter extends ArtisteFilter
{
	public static final String ID = "a_name";
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
