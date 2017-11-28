package org.applicationn.search.criteria.spectacle;

public class PublicFilter extends SpectacleFilter
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
		return attribute("publicc") + " IN (" + String.join(",", publc) + ")";
	}
}
