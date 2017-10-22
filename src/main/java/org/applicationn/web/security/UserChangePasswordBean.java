package org.applicationn.web.security;

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.applicationn.domain.security.UserEntity;
import org.applicationn.domain.security.UserStatus;
import org.applicationn.service.security.SecurityWrapper;
import org.applicationn.service.security.UserService;
import org.applicationn.web.util.MessageFactory;

/**
 * Managed bean for pages/user/changePassword.xhtml
 * */
@ManagedBean
@ViewScoped
public class UserChangePasswordBean implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(UserChangePasswordBean.class.getName());

    private String key;

    @Inject
    private UserService userService;

    private UserEntity keyUserEntity;

    final double URL_VALID_PERIOD_IN_HOURS = 24;

    private boolean valid = false;
    private String new_password;
    private String new_password_repeat;

    /**
     * Check validity of url (check is there a user with url 'key' param value; check is url life time not expired)
     * */
    @PostConstruct
    public void init() {
        this.key = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key");
        if(this.key == null){
            this.valid = false;
            return;
        }

        this.keyUserEntity = this.userService.findUserByEmailResetPasswordKey(key);
        if(this.keyUserEntity != null) {

            // if authorized user does not match with key user
            if(this.keyUserEntity.getUsername().equals(SecurityWrapper.getUsername())) {
                valid = false; // then no access to change password
            }

            Date passwordResetDate = this.keyUserEntity.getPasswordResetDate();
            boolean expired = new Date().getTime() - passwordResetDate.getTime()
                    > this.URL_VALID_PERIOD_IN_HOURS * 1000 * 60 * 60;
            this.valid = !expired && this.keyUserEntity.getStatus().equals(UserStatus.Active);
        }
    }

    /**
     * If url is valid , user with url 'key' param value exist and user has status 'active' and if new_password equals
     * new_password_repeat, than method change user's password
     * */
    public void changePassword(){
        FacesMessage facesMessage = null;
        try {
            if (valid && this.keyUserEntity != null && this.keyUserEntity.getStatus().equals(UserStatus.Active)) {
                facesMessage = this.userService.changePassword(this.keyUserEntity, this.new_password, this.new_password_repeat);
            }
        } catch (RuntimeException e){
            logger.log(Level.SEVERE, "Error on reseting user's password", e);
            facesMessage = MessageFactory.getMessage("error_while_changing_password");
            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        } finally {
            if (facesMessage != null) {
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            }
        }
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password_repeat(String new_password_repeat) {
        this.new_password_repeat = new_password_repeat;
    }

    public String getNew_password_repeat() {
        return new_password_repeat;
    }
}
