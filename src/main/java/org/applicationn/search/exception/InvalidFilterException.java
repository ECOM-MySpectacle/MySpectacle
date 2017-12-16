package org.applicationn.search.exception;

/**
 * Thrown when a filter is syntactically correct but contains invalid data.
 */
public class InvalidFilterException extends FilterException
{
	public InvalidFilterException(String filter)
	{
		super(filter);
	}
}
