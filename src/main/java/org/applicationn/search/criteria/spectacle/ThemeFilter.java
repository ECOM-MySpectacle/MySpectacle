package org.applicationn.search.criteria.spectacle;

import org.applicationn.search.criteria.Filter;

public class ThemeFilter extends Filter
{
	public static final String ID = "theme";
	private final String theme;

	public ThemeFilter(String theme)
	{
		super(ID);

		this.theme = theme;
	}

	@Override
	public String condition()
	{
		return "o.theme = '" + theme + "'";
	}
}
