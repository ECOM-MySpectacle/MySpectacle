package org.applicationn.rest;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.applicationn.domain.ReservationEntity;
import org.applicationn.service.ReservationService;

@Path("/reservations")
@Named
public class ReservationResource implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private ReservationService reservationService;
    
    /**
     * Get the complete list of Reservation Entries <br/>
     * HTTP Method: GET <br/>
     * Example URL: /reservations
     * @return List of ReservationEntity (JSON)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ReservationEntity> getAllReservations() {
        return reservationService.findAllReservationEntities();
    }
    
    /**
     * Get the number of Reservation Entries <br/>
     * HTTP Method: GET <br/>
     * Example URL: /reservations/count
     * @return Number of ReservationEntity
     */
    @GET
    @Path("count")
    @Produces(MediaType.APPLICATION_JSON)
    public long getCount() {
        return reservationService.countAllEntries();
    }
    
    /**
     * Get a Reservation Entity <br/>
     * HTTP Method: GET <br/>
     * Example URL: /reservations/3
     * @param id
     * @return A Reservation Entity (JSON)
     */
    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public ReservationEntity getReservationById(@PathParam("id") Long id) {
        return reservationService.find(id);
    }
    
    /**
     * Create a Reservation Entity <br/>
     * HTTP Method: POST <br/>
     * POST Request Body: New ReservationEntity (JSON) <br/>
     * Example URL: /reservations
     * @param reservation
     * @return A ReservationEntity (JSON)
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ReservationEntity addReservation(ReservationEntity reservation) {
        return reservationService.save(reservation);
    }
    
    /**
     * Update an existing Reservation Entity <br/>
     * HTTP Method: PUT <br/>
     * PUT Request Body: Updated ReservationEntity (JSON) <br/>
     * Example URL: /reservations
     * @param reservation
     * @return A ReservationEntity (JSON)
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ReservationEntity updateReservation(ReservationEntity reservation) {
        return reservationService.update(reservation);
    }
    
    /**
     * Delete an existing Reservation Entity <br/>
     * HTTP Method: DELETE <br/>
     * Example URL: /reservations/3
     * @param id
     */
    @Path("{id}")
    @DELETE
    public void deleteReservation(@PathParam("id") Long id) {
        ReservationEntity reservation = reservationService.find(id);
        reservationService.delete(reservation);
    }
    
}
