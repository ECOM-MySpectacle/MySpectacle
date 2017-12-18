package org.applicationn.booking;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.List;

import org.applicationn.domain.ReservationModePaiement;

public class Booking implements Iterable<Booking.Entry>
{
	public final String email, name, token;
	public final ReservationModePaiement modePaiement;
	public final List<Entry> entries;

	public Booking(String email, String name, String token, ReservationModePaiement modePaiement, List<Entry> entries)
	{
		this.email = email;
		this.name = name;
		this.token = token;
		this.modePaiement = modePaiement;
		this.entries = entries;
	}

	@Override
	@Nonnull
	public Iterator<Entry> iterator()
	{
		return entries.iterator();
	}

	public static class Entry
	{
		public final long id;
		public final int balcon, fosse, orchestre;

		public Entry(long id, int balcon, int fosse, int orchestre)
		{
			this.id = id;
			this.balcon = balcon;
			this.fosse = fosse;
			this.orchestre = orchestre;
		}
	}
}
