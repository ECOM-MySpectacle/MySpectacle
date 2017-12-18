package org.applicationn.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.applicationn.booking.Booking;
import org.applicationn.booking.exception.BookingException;
import org.applicationn.booking.exception.InvalidBookingException;
import org.applicationn.booking.exception.InvalidTokenException;
import org.applicationn.domain.BaseEntity;
import org.applicationn.domain.RepresentationEntity;
import org.applicationn.domain.ReservationEntity;
import org.applicationn.service.security.RegistrationMailSender;

public class BookingService implements Serializable
{
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	private <T extends BaseEntity> void update(T entity)
	{
		entityManager.merge(entity);
	}

	@Transactional
	private <T extends BaseEntity> void save(T entity)
	{
		entityManager.persist(entity);
	}

	@Transactional
	public void commit()
	{
		entityManager.flush();
	}

	/**
	 * Validates the given token.
	 *
	 * @param token the token
	 * @throws InvalidTokenException if the token is invalid
	 */
	private void validateToken(String token) throws InvalidTokenException
	{
		/*
		if(!token.equals(...) || !token.matches(...) || !API.isValid(token))
		{
			throw new InvalidTokenException();
		}
		 */
	}

	@Transactional
	private RepresentationEntity getRepresentationBySpectacle(long spectacleId)
	{
		return entityManager.createQuery("SELECT r FROM Representation r WHERE r.spectacle IS NOT NULL AND r.spectacle.id=:id", RepresentationEntity.class).setParameter("id", spectacleId).getSingleResult();
	}

	@Transactional
	public void book(Booking booking) throws BookingException
	{
		// token validation
		validateToken(booking.token);

		Map<Long, String> spectacles = new HashMap<>();

		try
		{
			// for each booking in the basket
			for(Booking.Entry entry : booking.entries)
			{
				// retrieve the corresponding representation
				RepresentationEntity rp = getRepresentationBySpectacle(entry.id);

				// if it doesn't exist
				if(rp == null)
				{
					throw new InvalidBookingException(entry);
				}

				// save the name for later use
				spectacles.put(entry.id, rp.getSpectacle().getNom());

				// calculate left seats
				int balconLeft = rp.getNbPlacesBalconLibres() - entry.balcon, fosseLeft = rp.getNbPlacesFosseLibres() - entry.fosse, orchestreLeft = rp.getNbPlacesOrchestreLibres() - entry.orchestre;

				// if any value is negative (we can't have a negative number of seats left)
				if(balconLeft < 0 || fosseLeft < 0 || orchestreLeft < 0)
				{
					throw new InvalidBookingException(entry);
				}

				// update the representation
				rp.setNbPlacesBalconLibres(balconLeft);
				rp.setNbPlacesFosseLibres(fosseLeft);
				rp.setNbPlacesOrchestreLibres(orchestreLeft);

				// create a new reservation or booking
				ReservationEntity rs = new ReservationEntity();
				rs.setEmail(booking.email);
				rs.setNbPlaceBalcon(entry.balcon);
				rs.setNbPlaceFosse(entry.fosse);
				rs.setNbPlaceOrchestre(entry.orchestre);
				rs.setModePaiement(booking.modePaiement);
				rs.setRepresentation(rp);

				// store changes
				update(rp);
				save(rs);
			}
		}
		catch(BookingException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw new BookingException(e);
		}

		// success!
		commit();

		try
		{
			booking.entries.forEach(entry -> RegistrationMailSender.sendQRCode(booking.email, booking.name, Long.toString(entry.id), entry.balcon + entry.fosse + entry.orchestre, spectacles.get(entry.id)));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
