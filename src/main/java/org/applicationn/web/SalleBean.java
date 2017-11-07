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

import org.applicationn.domain.SalleEntity;
import org.applicationn.domain.security.UserEntity;
import org.applicationn.service.SalleService;
import org.applicationn.service.security.SecurityWrapper;
import org.applicationn.service.security.UserService;
import org.applicationn.web.util.MessageFactory;

@Named("salleBean")
@ViewScoped
public class SalleBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(SalleBean.class.getName());
    
    private List<SalleEntity> salleList;

    private SalleEntity salle;
    
    @Inject
    private SalleService salleService;
    
    @Inject
    private UserService userService;
    
    private List<UserEntity> allGestionnairesList;
    
    public void prepareNewSalle() {
        reset();
        this.salle = new SalleEntity();
        // set any default values now, if you need
        // Example: this.salle.setAnything("test");
    }

    public String persist() {

        if (salle.getId() == null && !isPermitted("salle:create")) {
            return "accessDenied";
        } else if (salle.getId() != null && !isPermitted(salle, "salle:update")) {
            return "accessDenied";
        }
        
        String message;
        
        try {
            
            if (salle.getId() != null) {
                salle = salleService.update(salle);
                message = "message_successfully_updated";
            } else {
                salle = salleService.save(salle);
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
        
        salleList = null;

        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
    }
    
    public String delete() {
        
        if (!isPermitted(salle, "salle:delete")) {
            return "accessDenied";
        }
        
        String message;
        
        try {
            salleService.delete(salle);
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
    
    public void onDialogOpen(SalleEntity salle) {
        reset();
        this.salle = salle;
    }
    
    public void reset() {
        salle = null;
        salleList = null;
        
        allGestionnairesList = null;
        
    }

    // Get a List of all gestionnaire
    public List<UserEntity> getGestionnaires() {
        if (this.allGestionnairesList == null) {
            this.allGestionnairesList = userService.findAllUserEntities();
        }
        return this.allGestionnairesList;
    }
    
    // Update gestionnaire of the current salle
    public void updateGestionnaire(UserEntity user) {
        this.salle.setGestionnaire(user);
        // Maybe we just created and assigned a new user. So reset the allGestionnaireList.
        allGestionnairesList = null;
    }
    
    public SalleEntity getSalle() {
        if (this.salle == null) {
            prepareNewSalle();
        }
        return this.salle;
    }
    
    public void setSalle(SalleEntity salle) {
        this.salle = salle;
    }
    
    public List<SalleEntity> getSalleList() {
        if (salleList == null) {
            salleList = salleService.findAllSalleEntities();
        }
        return salleList;
    }

    public void setSalleList(List<SalleEntity> salleList) {
        this.salleList = salleList;
    }
    
    public boolean isPermitted(String permission) {
        return SecurityWrapper.isPermitted(permission);
    }

    public boolean isPermitted(SalleEntity salle, String permission) {
        
        return SecurityWrapper.isPermitted(permission);
        
    }
    
}
