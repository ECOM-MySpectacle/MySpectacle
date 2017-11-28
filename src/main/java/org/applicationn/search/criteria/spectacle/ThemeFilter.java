package org.applicationn.search.criteria.spectacle;

public class ThemeFilter extends SpectacleFilter
{
	public static final String ID = "sp_theme";
	private final String theme;

	public ThemeFilter(String theme)
	{
		super(ID);

		this.theme = theme;
	}

	@Override
	public String condition()
	{
		return attribute("theme") + " = '" + theme + "'";
	}
}
