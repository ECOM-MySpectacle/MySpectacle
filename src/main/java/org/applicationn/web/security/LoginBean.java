package org.applicationn.web.security;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.applicationn.service.security.SecurityWrapper;
import org.applicationn.web.util.MessageFactory;

@Named
@RequestScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private boolean remember;

    public String login() throws IOException {
        
        if (!SecurityWrapper.login(username, password, remember)) {
            FacesMessage message = MessageFactory.getMessage(
                    "authentication_exception");
            FacesContext.getCurrentInstance().addMessage(null, message);
            return null;
        }
        
        SavedRequest savedRequest = WebUtils
                .getAndClearSavedRequest((HttpServletRequest) FacesContext
                        .getCurrentInstance().getExternalContext()
                        .getRequest());
        
        if (savedRequest != null) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(savedRequest.getRequestUrl());
            return null;
        } else {
            return "/pages/main?faces-redirect=true";
        }
    }

    public void logout() throws IOException {
        SecurityWrapper.logout();
        String path = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath() + "/pages/main.xhtml";
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().redirect(path);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }
}
