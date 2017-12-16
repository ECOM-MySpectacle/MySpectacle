package org.applicationn.search.exception;

/**
 * Thrown when an unknown filter identifier is encountered.
 */
public class UnknownFilterException extends FilterException
{
	public UnknownFilterException(String filter)
	{
		super(filter);
	}
}
