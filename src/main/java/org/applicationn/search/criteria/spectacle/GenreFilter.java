package org.applicationn.search.criteria.spectacle;

public class GenreFilter extends SpectacleFilter
{
	public static final String ID = "sp_genre";
	private final String[] genres;

	public GenreFilter(String[] genres)
	{
		super(ID);

		this.genres = genres;
	}

	@Override
	public String condition()
	{
		return attribute("genre") + " IN (" + String.join(",", genres) + ")";
	}
}
