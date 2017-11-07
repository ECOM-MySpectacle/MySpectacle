package org.applicationn.web;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;

import org.applicationn.domain.RepresentationEntity;
import org.applicationn.domain.SalleEntity;
import org.applicationn.service.RepresentationService;
import org.applicationn.service.SalleService;
import org.applicationn.service.security.SecurityWrapper;
import org.applicationn.web.util.MessageFactory;

@Named("representationBean")
@ViewScoped
public class RepresentationBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(RepresentationBean.class.getName());
    
    private List<RepresentationEntity> representationList;

    private RepresentationEntity representation;
    
    @Inject
    private RepresentationService representationService;
    
    @Inject
    private SalleService salleService;
    
    private List<SalleEntity> allSallesList;
    
    public void prepareNewRepresentation() {
        reset();
        this.representation = new RepresentationEntity();
        // set any default values now, if you need
        // Example: this.representation.setAnything("test");
    }

    public String persist() {

        if (representation.getId() == null && !isPermitted("representation:create")) {
            return "accessDenied";
        } else if (representation.getId() != null && !isPermitted(representation, "representation:update")) {
            return "accessDenied";
        }
        
        String message;
        
        try {
            
            if (representation.getId() != null) {
                representation = representationService.update(representation);
                message = "message_successfully_updated";
            } else {
                representation = representationService.save(representation);
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
        
        representationList = null;

        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
    }
    
    public String delete() {
        
        if (!isPermitted(representation, "representation:delete")) {
            return "accessDenied";
        }
        
        String message;
        
        try {
            representationService.delete(representation);
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
    
    public void onDialogOpen(RepresentationEntity representation) {
        reset();
        this.representation = representation;
    }
    
    public void reset() {
        representation = null;
        representationList = null;
        
        allSallesList = null;
        
    }

    // Get a List of all salle
    public List<SalleEntity> getSalles() {
        if (this.allSallesList == null) {
            this.allSallesList = salleService.findAllSalleEntities();
        }
        return this.allSallesList;
    }
    
    // Update salle of the current representation
    public void updateSalle(SalleEntity salle) {
        this.representation.setSalle(salle);
        // Maybe we just created and assigned a new salle. So reset the allSalleList.
        allSallesList = null;
    }
    
    public RepresentationEntity getRepresentation() {
        if (this.representation == null) {
            prepareNewRepresentation();
        }
        return this.representation;
    }
    
    public void setRepresentation(RepresentationEntity representation) {
        this.representation = representation;
    }
    
    public List<RepresentationEntity> getRepresentationList() {
        if (representationList == null) {
            representationList = representationService.findAllRepresentationEntities();
        }
        return representationList;
    }

    public void setRepresentationList(List<RepresentationEntity> representationList) {
        this.representationList = representationList;
    }
    
    public boolean isPermitted(String permission) {
        return SecurityWrapper.isPermitted(permission);
    }

    public boolean isPermitted(RepresentationEntity representation, String permission) {
        
        return SecurityWrapper.isPermitted(permission);
        
    }
    
}
