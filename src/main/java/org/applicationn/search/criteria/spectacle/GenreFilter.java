package org.applicationn.search.criteria.spectacle;

import org.applicationn.search.criteria.Filter;

public class GenreFilter extends Filter
{
	public static final String ID = "genre";
	private final String[] genres;

	public GenreFilter(String[] genres)
	{
		super(ID);

		this.genres = genres;
	}

	@Override
	public String condition()
	{
		return "o.genre IN (" + String.join(",", genres) + ")";
	}
}
