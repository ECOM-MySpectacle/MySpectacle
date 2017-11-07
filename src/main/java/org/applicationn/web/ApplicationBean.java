package org.applicationn.web;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@ApplicationScoped
public class ApplicationBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // Used on edit and create dialogs to check if a certain "add entity" dialog component exists on page
    public boolean componentExists(String id) {
        return FacesContext.getCurrentInstance().getViewRoot().findComponent(id) != null; 
    }
    
}
