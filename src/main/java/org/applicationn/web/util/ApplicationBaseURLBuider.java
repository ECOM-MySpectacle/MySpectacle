package org.applicationn.web.util;

import java.net.MalformedURLException;
import java.net.URL;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Class-helper for getting application's base URL
 * */
public class ApplicationBaseURLBuider {

    /**
     * @return application's base URL e.g. http://example.com
     * */
    public static String getURL(){
        HttpServletRequest request = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest());
        String baseURL;
        try {
            baseURL = new URL(request.getScheme(),
                    request.getServerName(),
                    request.getServerPort(),
                    request.getContextPath()).toString();
            return baseURL;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
