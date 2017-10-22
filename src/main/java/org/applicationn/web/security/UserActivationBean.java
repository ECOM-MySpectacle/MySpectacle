package org.applicationn.web.security;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

import org.applicationn.domain.security.UserEntity;
import org.applicationn.domain.security.UserStatus;
import org.applicationn.service.security.UserService;

/**
 * Managed bean for pages/user/activation.xhtml
 * */
@ManagedBean
@RequestScoped
public class UserActivationBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManagedProperty(value = "#{param.key}")
    private String key;

    @Inject
    private UserService userService;

    final double URL_VALID_PERIOD_IN_HOURS = 24;

    private boolean valid = false;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    /**
     * Check validity of url (check is there a user with url 'key' param value;
     * check is url life time not expired) If it's valid - change user status to
     * 'Active'
     * */
    @PostConstruct
    public void init() {
        UserEntity userEntity = this.userService
                .findUserByEmailConfirmationKey(this.key);
        if (userEntity != null) {
            Date creationDate = userEntity.getCreatedAt();
            this.valid = new Date().getTime() - creationDate.getTime()
                    <= this.URL_VALID_PERIOD_IN_HOURS * 1000 * 60 * 60;
            if (userEntity.getStatus().equals(UserStatus.NotConfirmed)) {
                if (this.valid) {
                    userEntity.setStatus(UserStatus.Active);
                    this.userService.update(userEntity);
                } else {
                    userEntity.setStatus(UserStatus.RegistrationError);
                    this.userService.update(userEntity);
                }
            }
        }
    }

}
