package org.applicationn.search.criteria;

/**
 * Thrown when a filter is syntactically incorrect.
 */
public class MalformedFilterException extends Exception
{
	/**
	 * The filter identifier
	 */
	private final String filter;

	public MalformedFilterException(String filter)
	{
		this.filter = filter;
	}

	public String getFilter()
	{
		return filter;
	}
}
