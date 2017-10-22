package org.applicationn.web.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;

@Named
@SessionScoped
public class LocaleBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private Locale locale;

    @PostConstruct
    public void init() {
        this.locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }

    public Locale getLocale() {
        return this.locale;
    }

    /**
     * Returns an array of all supported locales to display them in the language selector
     * @return Array of SelectItem
     */
    public SelectItem[] getLocales() {
        List<SelectItem> items = new ArrayList<>();
        Iterator<Locale> supportedLocales = FacesContext.getCurrentInstance().getApplication().getSupportedLocales();
        while (supportedLocales.hasNext()) {
            Locale supportedLocale = supportedLocales.next();
            items.add(new SelectItem(supportedLocale.toString(), supportedLocale.getDisplayName(supportedLocale)));
        }
        return items.toArray(new SelectItem[] {});
    }

    public String getSelectedLocale() {
    	if (this.locale == null || this.locale.getLanguage() == null) {
    		return null;
    	}
        return this.locale.getLanguage();
    }
    
   public void setSelectedLocale(String localeString) {
        Iterator<Locale> supportedLocales = FacesContext.getCurrentInstance().getApplication().getSupportedLocales();
        while (supportedLocales.hasNext()) {
            Locale supportedLocale = supportedLocales.next();
            if (supportedLocale.toString().equals(localeString)) {
            	this.locale = supportedLocale;
                break;
            }
        }
    }
}