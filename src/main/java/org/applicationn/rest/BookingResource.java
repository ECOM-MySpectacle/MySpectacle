package org.applicationn.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.json.*;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.applicationn.booking.Booking;
import org.applicationn.booking.exception.BookingException;
import org.applicationn.booking.exception.InvalidBookingException;
import org.applicationn.booking.exception.InvalidPaymentMethodException;
import org.applicationn.booking.exception.InvalidTokenException;
import org.applicationn.domain.ReservationModePaiement;
import org.applicationn.service.BookingService;

@Path("/booking")
@Named
public class BookingResource implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Inject
	private BookingService bookingService;

	private String error(String message)
	{
		return String.format("{\"error\":\"%s\"}", message);
	}

	private String badRequest()
	{
		return error("Bad request");
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String post(String json)
	{
		Booking booking;

		try
		{
			JsonObject o;

			try(JsonReader reader = Json.createReader(new StringReader(json)))
			{
				o = reader.readObject();
			}

			String email = o.getString("mail");
			String name = o.getString("nom");
			String token = o.getString("token");

			ReservationModePaiement rmp;

			if(o.containsKey("paiement"))
			{
				String modePaiement = o.getString("paiement");
				rmp = Arrays.stream(ReservationModePaiement.values()).filter(p -> p.name().equalsIgnoreCase(modePaiement)).findFirst().orElse(null);

				if(rmp == null)
				{
					throw new InvalidPaymentMethodException();
				}
			}
			else
			{
				rmp = ReservationModePaiement.CB;
			}

			JsonArray representations = o.getJsonArray("spectacles");
			List<Booking.Entry> l = new ArrayList<>(representations.size());

			for(JsonValue v : representations)
			{
				JsonObject representation = (JsonObject) v;

				long id = representation.getInt("id");
				int seats = representation.getInt("quantite");
				int balcon, fosse, orchestre;

				switch(representation.getString("position").toLowerCase())
				{
					case "balcon":
					{
						balcon = seats;
						fosse = orchestre = 0;

						break;
					}

					case "fosse":
					{
						fosse = seats;
						balcon = orchestre = 0;

						break;
					}

					case "orchestre":
					{
						orchestre = seats;
						balcon = fosse = 0;

						break;
					}

					default:
					{
						throw new BookingException();
					}
				}

				l.add(new Booking.Entry(id, balcon, fosse, orchestre));
			}

			booking = new Booking(email, name, token, rmp, l);
		}
		catch(InvalidPaymentMethodException e)
		{
			return error("Invalid payment method");
		}
		catch(Exception e)
		{
			System.out.println(e);
			return badRequest();
		}

		try
		{
			Logger.getLogger(BookingResource.class.getName()).log(Level.INFO, "[tirandule]Pre booking");
			bookingService.book(booking);
			Logger.getLogger(BookingResource.class.getName()).log(Level.INFO, "[tirandule]Post booking");
			
		}
		catch(InvalidTokenException e)
		{
			return error("Invalid token");
		}
		catch(InvalidBookingException e)
		{
			return error("Invalid booking");
		}
		catch(Exception e)
		{
			return error(e.getMessage());
		}

		return error("OK");
	}
}
