package org.applicationn.search.criteria.spectacle;

import org.applicationn.search.criteria.InvalidFilterException;

public class ArtistFilter extends SpectacleFilter
{
	public static final String ID = "sp_artist";
	private final String name;

	public ArtistFilter(String name) throws InvalidFilterException
	{
		super(ID);

		if(name == null || name.isEmpty())
		{
			throw new InvalidFilterException(ID);
		}

		this.name = name;
	}

	@Override
	public String condition()
	{
		return attribute("artiste") + " LIKE '%" + name + "'%";
	}
}
