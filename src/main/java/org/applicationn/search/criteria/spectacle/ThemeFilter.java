package org.applicationn.search.criteria.spectacle;

import org.applicationn.search.criteria.InvalidFilterException;

public class ThemeFilter extends SpectacleFilter
{
	public static final String ID = "sp_theme";
	private final String theme;

	public ThemeFilter(String theme) throws InvalidFilterException
	{
		super(ID);

		if(theme == null || theme.isEmpty())
		{
			throw new InvalidFilterException(ID);
		}

		this.theme = theme;
	}

	@Override
	public String condition()
	{
		return attribute("theme") + " = '" + theme + "'";
	}
}
