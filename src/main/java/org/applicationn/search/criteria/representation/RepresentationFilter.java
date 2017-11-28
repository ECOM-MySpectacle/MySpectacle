package org.applicationn.search.criteria.representation;

import org.applicationn.search.criteria.Filter;

public abstract class RepresentationFilter extends Filter
{
	public RepresentationFilter(String id)
	{
		super(id);
	}

	@Override
	public String attribute(String attr)
	{
		return "r." + attr;
	}
}
