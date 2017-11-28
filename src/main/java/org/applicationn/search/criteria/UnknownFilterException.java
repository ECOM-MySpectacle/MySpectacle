package org.applicationn.search.criteria;

public class UnknownFilterException extends Exception
{
	private final String filter;

	public UnknownFilterException(String filter)
	{
		this.filter = filter;
	}

	public String getFilter()
	{
		return filter;
	}
}
