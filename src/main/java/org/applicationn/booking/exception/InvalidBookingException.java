package org.applicationn.booking.exception;

import org.applicationn.booking.Booking;

public class InvalidBookingException extends BookingException
{
	private final Booking.Entry entry;

	public InvalidBookingException(Booking.Entry entry)
	{
		this.entry = entry;
	}

	public Booking.Entry getInvalidRepresentation()
	{
		return entry;
	}
}
