package org.applicationn.search.criteria.artiste;

import org.applicationn.search.criteria.Filter;

abstract class ArtisteFilter extends Filter
{
	ArtisteFilter(String id)
	{
		super(id);
	}

	@Override
	protected String attribute(String attr)
	{
		return "a." + attr;
	}
}
