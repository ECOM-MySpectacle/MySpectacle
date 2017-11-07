package org.applicationn.web;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.applicationn.domain.ReservationEntity;
import org.applicationn.domain.ReservationModePaiement;
import org.applicationn.service.ReservationService;
import org.applicationn.service.security.SecurityWrapper;
import org.applicationn.web.util.MessageFactory;

@Named("reservationBean")
@ViewScoped
public class ReservationBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(ReservationBean.class.getName());
    
    private List<ReservationEntity> reservationList;

    private ReservationEntity reservation;
    
    @Inject
    private ReservationService reservationService;
    
    public void prepareNewReservation() {
        reset();
        this.reservation = new ReservationEntity();
        // set any default values now, if you need
        // Example: this.reservation.setAnything("test");
    }

    public String persist() {

        if (reservation.getId() == null && !isPermitted("reservation:create")) {
            return "accessDenied";
        } else if (reservation.getId() != null && !isPermitted(reservation, "reservation:update")) {
            return "accessDenied";
        }
        
        String message;
        
        try {
            
            if (reservation.getId() != null) {
                reservation = reservationService.update(reservation);
                message = "message_successfully_updated";
            } else {
                reservation = reservationService.save(reservation);
                message = "message_successfully_created";
            }
        } catch (OptimisticLockException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_optimistic_locking_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_save_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
        
        reservationList = null;

        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
    }
    
    public String delete() {
        
        if (!isPermitted(reservation, "reservation:delete")) {
            return "accessDenied";
        }
        
        String message;
        
        try {
            reservationService.delete(reservation);
            message = "message_successfully_deleted";
            reset();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error occured", e);
            message = "message_delete_exception";
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
        FacesContext.getCurrentInstance().addMessage(null, MessageFactory.getMessage(message));
        
        return null;
    }
    
    public void onDialogOpen(ReservationEntity reservation) {
        reset();
        this.reservation = reservation;
    }
    
    public void reset() {
        reservation = null;
        reservationList = null;
        
    }

    public SelectItem[] getModePaiementSelectItems() {
        SelectItem[] items = new SelectItem[ReservationModePaiement.values().length];

        int i = 0;
        for (ReservationModePaiement modePaiement : ReservationModePaiement.values()) {
            items[i++] = new SelectItem(modePaiement, getLabelForModePaiement(modePaiement));
        }
        return items;
    }
    
    public String getLabelForModePaiement(ReservationModePaiement value) {
        if (value == null) {
            return "";
        }
        String label = MessageFactory.getMessageString(
                "enum_label_reservation_modePaiement_" + value);
        return label == null? value.toString() : label;
    }
    
    public ReservationEntity getReservation() {
        if (this.reservation == null) {
            prepareNewReservation();
        }
        return this.reservation;
    }
    
    public void setReservation(ReservationEntity reservation) {
        this.reservation = reservation;
    }
    
    public List<ReservationEntity> getReservationList() {
        if (reservationList == null) {
            reservationList = reservationService.findAllReservationEntities();
        }
        return reservationList;
    }

    public void setReservationList(List<ReservationEntity> reservationList) {
        this.reservationList = reservationList;
    }
    
    public boolean isPermitted(String permission) {
        return SecurityWrapper.isPermitted(permission);
    }

    public boolean isPermitted(ReservationEntity reservation, String permission) {
        
        return SecurityWrapper.isPermitted(permission);
        
    }
    
}
