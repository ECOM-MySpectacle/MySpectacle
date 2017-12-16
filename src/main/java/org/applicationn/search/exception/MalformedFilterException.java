package org.applicationn.search.exception;

/**
 * Thrown when a filter is syntactically incorrect.
 */
public class MalformedFilterException extends FilterException
{
	public MalformedFilterException(String filter)
	{
		super(filter);
	}
}
