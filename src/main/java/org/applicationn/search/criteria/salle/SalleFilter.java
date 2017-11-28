package org.applicationn.search.criteria.salle;

import org.applicationn.search.criteria.Filter;

public abstract class SalleFilter extends Filter
{
	public SalleFilter(String id)
	{
		super(id);
	}

	@Override
	public String attribute(String attr)
	{
		return "sa." + attr;
	}
}
