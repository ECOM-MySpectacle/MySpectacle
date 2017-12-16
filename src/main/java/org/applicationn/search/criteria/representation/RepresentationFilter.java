package org.applicationn.search.criteria.representation;

import org.applicationn.search.criteria.Filter;

abstract class RepresentationFilter extends Filter
{
	RepresentationFilter(String id)
	{
		super(id);
	}

	@Override
	protected String tableVarName()
	{
		return "r";
	}
}
