package org.applicationn.booking.exception;

public class BookingException extends Exception
{
	public BookingException()
	{
		super();
	}

	public BookingException(String message)
	{
		super(message);
	}

	public BookingException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public BookingException(Throwable cause)
	{
		super(cause);
	}
}
