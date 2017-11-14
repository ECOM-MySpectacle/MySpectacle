package org.applicationn.search.criteria.spectacle;

import org.applicationn.search.criteria.Filter;

public class PublicFilter extends Filter
{
	public static final String ID = "public";
	private final String[] publc;

	public PublicFilter(String[] publc)
	{
		super(ID);

		this.publc = publc;
	}

	@Override
	public String condition()
	{
		return "o.publicc IN (" + String.join(",", publc) + ")";
	}
}
