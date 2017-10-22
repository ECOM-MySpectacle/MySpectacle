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

import org.applicationn.domain.SpectacleCible;
import org.applicationn.domain.SpectacleEntity;
import org.applicationn.domain.SpectacleType;
import org.applicationn.service.SpectacleService;
import org.applicationn.service.security.SecurityWrapper;
import org.applicationn.web.util.MessageFactory;

@Named("spectacleBean")
@ViewScoped
public class SpectacleBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(SpectacleBean.class.getName());
    
    private List<SpectacleEntity> spectacleList;

    private SpectacleEntity spectacle;
    
    @Inject
    private SpectacleService spectacleService;
    
    public void prepareNewSpectacle() {
        reset();
        this.spectacle = new SpectacleEntity();
        // set any default values now, if you need
        // Example: this.spectacle.setAnything("test");
    }

    public String persist() {

        if (spectacle.getId() == null && !isPermitted("spectacle:create")) {
            return "accessDenied";
        } else if (spectacle.getId() != null && !isPermitted(spectacle, "spectacle:update")) {
            return "accessDenied";
        }
        
        String message;
        
        try {
            
            if (spectacle.getId() != null) {
                spectacle = spectacleService.update(spectacle);
                message = "message_successfully_updated";
            } else {
                spectacle = spectacleService.save(spectacle);
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
        
        spectacleList = null;

        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
    }
    
    public String delete() {
        
        if (!isPermitted(spectacle, "spectacle:delete")) {
            return "accessDenied";
        }
        
        String message;
        
        try {
            spectacleService.delete(spectacle);
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
    
    public void onDialogOpen(SpectacleEntity spectacle) {
        reset();
        this.spectacle = spectacle;
    }
    
    public void reset() {
        spectacle = null;
        spectacleList = null;
        
    }

    public SelectItem[] getTypeSelectItems() {
        SelectItem[] items = new SelectItem[SpectacleType.values().length];

        int i = 0;
        for (SpectacleType type : SpectacleType.values()) {
            items[i++] = new SelectItem(type, getLabelForType(type));
        }
        return items;
    }
    
    public String getLabelForType(SpectacleType value) {
        if (value == null) {
            return "";
        }
        String label = MessageFactory.getMessageString(
                "enum_label_spectacle_type_" + value);
        return label == null? value.toString() : label;
    }
    
    public SelectItem[] getCibleSelectItems() {
        SelectItem[] items = new SelectItem[SpectacleCible.values().length];

        int i = 0;
        for (SpectacleCible cible : SpectacleCible.values()) {
            items[i++] = new SelectItem(cible, getLabelForCible(cible));
        }
        return items;
    }
    
    public String getLabelForCible(SpectacleCible value) {
        if (value == null) {
            return "";
        }
        String label = MessageFactory.getMessageString(
                "enum_label_spectacle_cible_" + value);
        return label == null? value.toString() : label;
    }
    
    public SpectacleEntity getSpectacle() {
        if (this.spectacle == null) {
            prepareNewSpectacle();
        }
        return this.spectacle;
    }
    
    public void setSpectacle(SpectacleEntity spectacle) {
        this.spectacle = spectacle;
    }
    
    public List<SpectacleEntity> getSpectacleList() {
        if (spectacleList == null) {
            spectacleList = spectacleService.findAllSpectacleEntities();
        }
        return spectacleList;
    }

    public void setSpectacleList(List<SpectacleEntity> spectacleList) {
        this.spectacleList = spectacleList;
    }
    
    public boolean isPermitted(String permission) {
        return SecurityWrapper.isPermitted(permission);
    }

    public boolean isPermitted(SpectacleEntity spectacle, String permission) {
        
        return SecurityWrapper.isPermitted(permission);
        
    }
    
}
