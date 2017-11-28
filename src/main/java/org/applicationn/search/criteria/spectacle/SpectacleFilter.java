package org.applicationn.search.criteria.spectacle;

import org.applicationn.search.criteria.Filter;

public abstract class SpectacleFilter extends Filter
{
	public SpectacleFilter(String id)
	{
		super(id);
	}

	@Override
	public String attribute(String attr)
	{
		return "sp." + attr;
	}
}
