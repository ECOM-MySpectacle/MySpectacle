package org.applicationn.search.criteria.spectacle;

import org.applicationn.search.criteria.Filter;

abstract class SpectacleFilter extends Filter
{
	SpectacleFilter(String id)
	{
		super(id);
	}

	@Override
	protected String tableVarName()
	{
		return "sp";
	}
}
