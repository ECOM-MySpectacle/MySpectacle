package org.applicationn.search.exception;

/**
 * Base class for filter related exceptions.
 */
public class FilterException extends Exception
{
	/**
	 * The filter identifier
	 */
	private final String filter;

	public FilterException(String filter)
	{
		this.filter = filter;
	}

	public String getFilter()
	{
		return filter;
	}
}
