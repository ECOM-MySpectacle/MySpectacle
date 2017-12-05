package org.applicationn.search.criteria.salle;

import org.applicationn.search.criteria.Filter;

abstract class SalleFilter extends Filter
{
	SalleFilter(String id)
	{
		super(id);
	}

	@Override
	protected String attribute(String attr)
	{
		return "sa." + attr;
	}
}
