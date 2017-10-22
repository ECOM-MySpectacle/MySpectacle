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

import org.applicationn.domain.PanierEntity;
import org.applicationn.service.PanierService;
import org.applicationn.service.security.SecurityWrapper;
import org.applicationn.web.util.MessageFactory;

@Named("panierBean")
@ViewScoped
public class PanierBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(PanierBean.class.getName());
    
    private List<PanierEntity> panierList;

    private PanierEntity panier;
    
    @Inject
    private PanierService panierService;
    
    public void prepareNewPanier() {
        reset();
        this.panier = new PanierEntity();
        // set any default values now, if you need
        // Example: this.panier.setAnything("test");
    }

    public String persist() {

        if (panier.getId() == null && !isPermitted("panier:create")) {
            return "accessDenied";
        } else if (panier.getId() != null && !isPermitted(panier, "panier:update")) {
            return "accessDenied";
        }
        
        String message;
        
        try {
            
            if (panier.getId() != null) {
                panier = panierService.update(panier);
                message = "message_successfully_updated";
            } else {
                panier = panierService.save(panier);
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
        
        panierList = null;

        FacesMessage facesMessage = MessageFactory.getMessage(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        
        return null;
    }
    
    public String delete() {
        
        if (!isPermitted(panier, "panier:delete")) {
            return "accessDenied";
        }
        
        String message;
        
        try {
            panierService.delete(panier);
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
    
    public void onDialogOpen(PanierEntity panier) {
        reset();
        this.panier = panier;
    }
    
    public void reset() {
        panier = null;
        panierList = null;
        
    }

    public PanierEntity getPanier() {
        if (this.panier == null) {
            prepareNewPanier();
        }
        return this.panier;
    }
    
    public void setPanier(PanierEntity panier) {
        this.panier = panier;
    }
    
    public List<PanierEntity> getPanierList() {
        if (panierList == null) {
            panierList = panierService.findAllPanierEntities();
        }
        return panierList;
    }

    public void setPanierList(List<PanierEntity> panierList) {
        this.panierList = panierList;
    }
    
    public boolean isPermitted(String permission) {
        return SecurityWrapper.isPermitted(permission);
    }

    public boolean isPermitted(PanierEntity panier, String permission) {
        
        return SecurityWrapper.isPermitted(permission);
        
    }
    
}
