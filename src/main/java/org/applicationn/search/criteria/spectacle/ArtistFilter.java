package org.applicationn.search.criteria.spectacle;

public class ArtistFilter extends SpectacleFilter
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
		return attribute("artiste") + " LIKE '%" + name + "'%";
	}
}
