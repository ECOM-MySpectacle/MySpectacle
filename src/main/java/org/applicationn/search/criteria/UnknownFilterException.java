package org.applicationn.search.criteria;

/**
 * Thrown when an unknown filter identifier is encountered.
 */
public class UnknownFilterException extends Exception
{
	/**
	 * The unknown identifier
	 */
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
