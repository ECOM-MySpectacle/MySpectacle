package org.applicationn.web.security;

import java.io.Serializable;
import java.util.ArrayList;
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

import org.applicationn.domain.SalleEntity;
import org.applicationn.domain.security.UserEntity;
import org.applicationn.domain.security.UserRole;
import org.applicationn.domain.security.UserStatus;
import org.applicationn.service.SalleService;
import org.applicationn.service.security.SecurityWrapper;
import org.applicationn.service.security.UserService;
import org.applicationn.web.generic.GenericLazyDataModel;
import org.applicationn.web.util.MessageFactory;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;

@Named("userBean")
@ViewScoped
public class UserBean implements Serializable {
    
    private static final Logger logger = Logger.getLogger(UserBean.class.getName());
    
    private LazyDataModel<UserEntity> lazyModel;

    private static final long serialVersionUID = 1L;

    private UserEntity user;

    @Inject
    private UserService userService;
    
    @Inject
    private SalleService salleService;
    
    private DualListModel<SalleEntity> salles;
    private List<String> transferedSalleIDs;
    private List<String> removedSalleIDs;
    
    public UserEntity getUser() {
        if (user == null) {
            user = new UserEntity();
        }
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void prepareNewUser() {
        this.user = new UserEntity();
        // set any default values now, if you need
    }
    
    public LazyDataModel<UserEntity> getLazyModel() {
        if (this.lazyModel == null) {
            this.lazyModel = new GenericLazyDataModel<>(userService);
        }
        return this.lazyModel;
    }
    
    public void persist() {

        String message;
        
        try {
            
            if (user.getId() != null) {
                user = userService.update(user);
                message = "message_successfully_updated";
            } else {
                
                // Check if a user with same username already exists
                if (userService.findUserByUsername(user.getUsername()) != null) {
                    FacesMessage facesMessage = MessageFactory.getMessage(
                            "user_username_exists");
                    facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
                    FacesContext.getCurrentInstance().validationFailed();
                    return;
                }
                // Check if a user with same email already exists            
                if (userService.findUserByEmail(user.getEmail()) != null) {
                    FacesMessage facesMessage = MessageFactory.getMessage(
                            "user_email_exists");
                    facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext.getCurrentInstance().addMessage(null, facesMessage);
                    FacesContext.getCurrentInstance().validationFailed();
                    return;
                }
                
                user = userService.save(user);
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
        
        FacesMessage facesMessage = MessageFactory.getMessage(message,
                "User");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    public String delete() {
        userService.delete(user);
        FacesMessage facesMessage = MessageFactory.getMessage(
                "message_successfully_deleted", "User");
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
        reset();
        return null;
    }
    
    public void reset() {
        user = null;
        
        salles = null;
        transferedSalleIDs = null;
        removedSalleIDs = null;
        
    }
    
    public SelectItem[] getRolesSelectItems() {
        SelectItem[] items = new SelectItem[UserRole.values().length];

        int i = 0;
        for (UserRole role : UserRole.values()) {
            String label = MessageFactory.getMessageString(
                    "enumeration_label_user_roles_" + role.toString());
            items[i++] = new SelectItem(role, label == null? role.toString() : label);
        }
        return items;
    }
    
    public SelectItem[] getStatusSelectItems() {
        SelectItem[] items = new SelectItem[UserStatus.values().length];

        int i = 0;
        for (UserStatus status : UserStatus.values()) {
            items[i++] = new SelectItem(status, status.toString());
        }
        return items;
    }
    
    public DualListModel<SalleEntity> getSalles() {
        return salles;
    }

    public void setSalles(DualListModel<SalleEntity> salles) {
        this.salles = salles;
    }
    
    public List<SalleEntity> getFullSallesList() {
        List<SalleEntity> allList = new ArrayList<>();
        allList.addAll(salles.getSource());
        allList.addAll(salles.getTarget());
        return allList;
    }
    
    public void onSallesDialog(UserEntity user) {
        // Prepare the salle PickList
        this.user = user;
        List<SalleEntity> selectedSallesFromDB = salleService
                .findSallesByGestionnaire(this.user);
        List<SalleEntity> availableSallesFromDB = salleService
                .findAvailableSalles(this.user);
        this.salles = new DualListModel<>(availableSallesFromDB, selectedSallesFromDB);
        
        transferedSalleIDs = new ArrayList<>();
        removedSalleIDs = new ArrayList<>();
    }
    
    public void onSallesPickListTransfer(TransferEvent event) {
        // If a salle is transferred within the PickList, we just transfer it in this
        // bean scope. We do not change anything it the database, yet.
        for (Object item : event.getItems()) {
            String id = ((SalleEntity) item).getId().toString();
            if (event.isAdd()) {
                transferedSalleIDs.add(id);
                removedSalleIDs.remove(id);
            } else if (event.isRemove()) {
                removedSalleIDs.add(id);
                transferedSalleIDs.remove(id);
            }
        }
        
    }
    
    public void updateSalle(SalleEntity salle) {
        // If a new salle is created, we persist it to the database,
        // but we do not assign it to this user in the database, yet.
        salles.getTarget().add(salle);
        transferedSalleIDs.add(salle.getId().toString());
    }
    
    public void onSallesSubmit() {
        // Now we save the changed of the PickList to the database.
        try {
            List<SalleEntity> selectedSallesFromDB = salleService
                    .findSallesByGestionnaire(this.user);
            List<SalleEntity> availableSallesFromDB = salleService
                    .findAvailableSalles(this.user);
            
            for (SalleEntity salle : selectedSallesFromDB) {
                if (removedSalleIDs.contains(salle.getId().toString())) {
                    salle.setGestionnaire(null);
                    salleService.update(salle);
                }
            }
    
            for (SalleEntity salle : availableSallesFromDB) {
                if (transferedSalleIDs.contains(salle.getId().toString())) {
                    salle.setGestionnaire(user);
                    salleService.update(salle);
                }
            }
            
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_changes_saved");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            
            reset();

        } catch (OptimisticLockException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_optimistic_locking_exception");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        } catch (PersistenceException e) {
            logger.log(Level.SEVERE, "Error occured", e);
            FacesMessage facesMessage = MessageFactory.getMessage(
                    "message_picklist_save_exception");
            FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            // Set validationFailed to keep the dialog open
            FacesContext.getCurrentInstance().validationFailed();
        }
    }
    
    public void loadCurrentUser() {
        String username = SecurityWrapper.getUsername();
        if (username != null) {
            this.user = this.userService.findUserByUsername(username);
        } else {
            throw new RuntimeException("Can not get authorized user name");
        }
    }
    
    public boolean isPermitted(String permission) {
        return SecurityWrapper.isPermitted(permission);
    }
}
