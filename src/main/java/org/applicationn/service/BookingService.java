package org.applicationn.service;

import javax.inject.Named;
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

@Named
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
	private void commit()
	{
		entityManager.flush();
	}

	@Transactional
	private void rollback()
	{
		entityManager.clear();
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
		// everything is done in transaction mode
		// entityManager is actually a transactional manager
		// see commit() and rollback() methods

		// token validation
		validateToken(booking.token);

		Map<Long, String> spectacles = new HashMap<>();

		try
		{
			// for each booking in the basket
			for(Booking.Entry entry : booking.entries)
			{
				// retrieve the corresponding representation
				// RepresentationEntity rp = getRepresentationBySpectacle(entry.id);
				RepresentationEntity rp = entityManager.find(RepresentationEntity.class, entry.id);

				// if it doesn't exist
				if(rp == null)
				{
					throw new InvalidBookingException("there is no representation for spectacle " + entry.id, entry);
				}

				// save the name of the spectacle for later use
				spectacles.put(entry.id, rp.getSpectacle().getNom());

				// calculate remaining seats
				int balconLeft = rp.getNbPlacesBalconLibres() - entry.balcon, fosseLeft = rp.getNbPlacesFosseLibres() - entry.fosse, orchestreLeft = rp.getNbPlacesOrchestreLibres() - entry.orchestre;

				// if any value is negative
				if(balconLeft < 0 || fosseLeft < 0 || orchestreLeft < 0)
				{
					throw new InvalidBookingException("there are not enough seats available for representation " + entry.id, entry);
				}

				// update the representation with new available seats
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

				// store changes in transaction
				update(rp);
				save(rs);
			}
		}
		catch(BookingException e)
		{
			rollback();

			throw e;
		}
		catch(Exception e)
		{
			rollback();

			throw new BookingException(e);
		}

		// success!
		// commit changes to database
		commit();

		try
		{
			booking.entries.forEach(entry -> RegistrationMailSender.sendQRCode(booking.email, booking.name, Long.toString(entry.id), entry.balcon + entry.fosse + entry.orchestre, spectacles.get(entry.id)));
		}
		catch(Exception e)
		{
			System.err.println("Could not send confirmation mail");

			e.printStackTrace();
		}
	}
}
