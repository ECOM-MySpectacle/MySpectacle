package org.applicationn.web.security;

import java.io.Serializable;
import java.security.Principal;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.mail.MessagingException;

import org.applicationn.domain.security.UserEntity;
import org.applicationn.domain.security.UserStatus;
import org.applicationn.service.security.RegistrationMailSender;
import org.applicationn.service.security.UserService;
import org.applicationn.web.util.ApplicationBaseURLBuider;
import org.applicationn.web.util.MessageFactory;

/**
 * Managed bean for pages/userManagement/resetPassword.xhtml
 * */
@ManagedBean
@ViewScoped
public class UserForgotPasswordHandlingBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(UserForgotPasswordHandlingBean.class.getName());

    @Inject
    private UserService userService;

    private boolean resetPasswordAccessGranted = true;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @PostConstruct
    public void init(){
        Principal userPrincipal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if(userPrincipal != null)
        {
            if (this.userService.findUserByUsername(userPrincipal.getName()) == null) { // if an authorized user not found in the database
                this.resetPasswordAccessGranted = false; // then no access to reset password
            }
        }
    }

    public void sendResetPasswordLink(){
        if (!this.resetPasswordAccessGranted) {
            /* no need in error messages here, user not supposed to access this method if
             * resetPasswordAccessGranted == false, since no send button show for him anyway */
            return;
        }

        try {
            UserEntity userByEmail = this.userService.findUserByEmail(this.email);
            if (userByEmail == null) {
                // Do not show an error message to user, else hackers can check with accounts exist
                FacesMessage message = MessageFactory.getMessage("password_reseting_success");
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else if (userByEmail.getStatus().equals(UserStatus.NotConfirmed)) {
                FacesMessage message = MessageFactory.getMessage("user_account_not_confirmed");
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                String emailResetPasswordKey = UUID.randomUUID().toString();
                userByEmail.setEmailResetPasswordKey(emailResetPasswordKey);

                userByEmail.setPasswordResetDate();

                String resetPasswordLink = ApplicationBaseURLBuider.getURL()
                        + "/pages/user/changePassword.xhtml?key=" + emailResetPasswordKey;

                RegistrationMailSender.sendPasswordReset(userByEmail.getEmail(), resetPasswordLink);

                this.userService.update(userByEmail);

                FacesMessage message = MessageFactory.getMessage("password_reseting_success");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } catch (RuntimeException e){
            logger.log(Level.SEVERE, "Error on forgot password handling", e);
            FacesMessage message;
            if(e.getCause() instanceof MessagingException) {
                message = MessageFactory.getMessage(
                        "sending_email_failed");
            } else {
                message = MessageFactory.getMessage(
                        "error_while_reseting_password");
            }
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public boolean getResetPasswordAccessGranted() {
        return resetPasswordAccessGranted;
    }
}
