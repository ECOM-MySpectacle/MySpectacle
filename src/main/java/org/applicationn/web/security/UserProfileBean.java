package org.applicationn.web.security;

import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.applicationn.domain.security.UserEntity;
import org.applicationn.service.security.UserService;
import org.applicationn.web.util.MessageFactory;

@Named
@ViewScoped
public class UserProfileBean implements Serializable {

    private static final Logger logger = Logger.getLogger(UserProfileBean.class.getName());

    private static final long serialVersionUID = 1L;

    private UserEntity user;

    @Inject
    private UserService userService;

    private String old_password;
    private String new_password;
    private String new_password_repeat;

    public void update() {
        FacesMessage facesMessage = null;
        try {
            Principal userPrincipal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
            if (userPrincipal != null) {
                String name = userPrincipal.getName();
                user = userService.findUserByUsername(name);
                facesMessage = this.userService.changePassword(user, new_password, new_password_repeat, old_password);
            } else {
                throw new RuntimeException("Can not get authorized user name");
            }
        } catch (RuntimeException e) {
            logger.log(Level.SEVERE, "Error on update user", e);
            facesMessage = MessageFactory.getMessage(
                    "error_while_changing_password");
            facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        } finally {
            if (facesMessage != null) {
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            }
        }
    }

    public String cancel() {
        return "/pages/main?faces-redirect=true";
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public void setNew_password_repeat(String new_password_repeat) {
        this.new_password_repeat = new_password_repeat;
    }

    public String getOld_password() {
        return "";
    }

    public String getNew_password() {
        return "";
    }

    public String getNew_password_repeat() {
        return "";
    }
}
