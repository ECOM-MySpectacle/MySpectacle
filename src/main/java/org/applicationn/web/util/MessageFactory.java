package org.applicationn.web.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessageFactory {

    public static FacesMessage getMessage(final String messageKey, final Object... params) {
		return new FacesMessage(getMessageString(messageKey, params));
    }
    
    public static String getMessageString(final String messageKey, final Object... params) {
    	FacesContext ctx = FacesContext.getCurrentInstance();

		ResourceBundle bundle = ctx.getApplication()
                .getResourceBundle(ctx, "messages");

		if (!bundle.containsKey(messageKey)) {
			return null;
		}
		
		String msg = bundle.getString(messageKey);

		if (params != null) {
			MessageFormat format = new MessageFormat(msg);
			msg = format.format(params);
		}

		return msg;
    }

}
