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

import org.applicationn.domain.ArtisteEntity;
import org.applicationn.service.ArtisteService;
import org.applicationn.service.security.SecurityWrapper;
import org.applicationn.web.util.MessageFactory;

@Named("artisteBean")
@ViewScoped
public class ArtisteBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(ArtisteBean.class.getName());
    
    private List<ArtisteEntity> artisteList;

    private ArtisteEntity artiste;
    
    @Inject
    private ArtisteService artisteService;
    
    public void prepareNewArtiste() {
        reset();
        this.artiste = new ArtisteEntity();
        // set any default values now, if you need
        // Example: this.artiste.setAnything("test");
    }

    public String persist() {

        if (artiste.getId() == null && !isPermitted("artiste:create")) {
            return "accessDenied";
        } else if (artiste.getId() != null && !isPermitted(artiste, "artiste:update")) {
            return "accessDenied";
        }
        
        String message;
        
        try {
            
            if (artiste.getId() != null) {
                artiste = artisteService.update(artiste);
                message = "message_successfully_updated";
            } else {
                artiste = artisteService.save(artiste);
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
        
        artisteList = null;

        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
    }
    
    public String delete() {
        
        if (!isPermitted(artiste, "artiste:delete")) {
            return "accessDenied";
        }
        
        String message;
        
        try {
            artisteService.delete(artiste);
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
    
    public void onDialogOpen(ArtisteEntity artiste) {
        reset();
        this.artiste = artiste;
    }
    
    public void reset() {
        artiste = null;
        artisteList = null;
        
    }

    public ArtisteEntity getArtiste() {
        if (this.artiste == null) {
            prepareNewArtiste();
        }
        return this.artiste;
    }
    
    public void setArtiste(ArtisteEntity artiste) {
        this.artiste = artiste;
    }
    
    public List<ArtisteEntity> getArtisteList() {
        if (artisteList == null) {
            artisteList = artisteService.findAllArtisteEntities();
        }
        return artisteList;
    }

    public void setArtisteList(List<ArtisteEntity> artisteList) {
        this.artisteList = artisteList;
    }
    
    public boolean isPermitted(String permission) {
        return SecurityWrapper.isPermitted(permission);
    }

    public boolean isPermitted(ArtisteEntity artiste, String permission) {
        
        return SecurityWrapper.isPermitted(permission);
        
    }
    
}
