package org.applicationn.search.criteria.spectacle;

import org.applicationn.search.criteria.Filter;

public class ArtistFilter extends Filter
{
	public static final String ID = "artist";
	private final String name;

	public ArtistFilter(String name)
	{
		super(ID);

		this.name = name;
	}

	@Override
	public String condition()
	{
		return "o.artiste LIKE '%" + name + "'%";
	}
}
