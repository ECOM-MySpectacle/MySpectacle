package org.applicationn.search.criteria;

public abstract class Filter
{
	private final String id;

	public Filter(String id)
	{
		this.id = id;
	}

	public final String getId()
	{
		return id;
	}

	public abstract String attribute(String attr);

	public abstract String condition();
}
