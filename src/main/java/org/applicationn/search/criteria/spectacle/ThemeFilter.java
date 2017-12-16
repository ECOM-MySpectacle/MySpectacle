package org.applicationn.search.criteria.spectacle;

import org.applicationn.search.criteria.InvalidFilterException;

public class ThemeFilter extends SpectacleFilter
{
	public static final String ID = "theme";

	public ThemeFilter(String theme) throws InvalidFilterException
	{
		super(ID);

		if(theme == null)
		{
			throw new InvalidFilterException(ID);
		}

		theme = theme.trim();

		if(theme.isEmpty())
		{
			throw new InvalidFilterException(ID);
		}

		setVar("theme", "%" + theme.toLowerCase() + "%");
	}

	@Override
	public String condition()
	{
		return lower("theme") + " = " + variable("theme");
	}
}
