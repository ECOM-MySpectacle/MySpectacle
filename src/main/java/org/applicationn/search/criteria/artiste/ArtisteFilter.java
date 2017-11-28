package org.applicationn.search.criteria.artiste;

import org.applicationn.search.criteria.Filter;

public abstract class ArtisteFilter extends Filter
{
	public ArtisteFilter(String id)
	{
		super(id);
	}

	@Override
	public String attribute(String attr)
	{
		return "a." + attr;
	}
}
