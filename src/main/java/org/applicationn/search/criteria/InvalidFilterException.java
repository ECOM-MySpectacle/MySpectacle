package org.applicationn.search.criteria;

/**
 * Thrown when a filter has invalid data.
 */
public class InvalidFilterException extends Exception
{
	/**
	 * The filter identifier
	 */
	private final String filter;

	public InvalidFilterException(String filter)
	{
		this.filter = filter;
	}

	public String getFilter()
	{
		return filter;
	}
}
