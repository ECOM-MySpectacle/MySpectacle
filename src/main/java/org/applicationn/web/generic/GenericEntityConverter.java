package org.applicationn.web.generic;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import org.applicationn.domain.BaseEntity;
import org.applicationn.service.BaseService;

@Named
public class GenericEntityConverter<T extends BaseEntity> implements Converter, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(GenericEntityConverter.class.getName());
	
	@Inject
	BaseService<T> baseService;

	@Override
	@SuppressWarnings({"unchecked"})
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {

        if (value == null || value.isEmpty() || component == null) {
            return null;
        }
        
        Class<T> type = (Class<T>) component.getValueExpression("value").getType(context.getELContext());
		return baseService.find(type, Long.valueOf(value));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {

		if (value == null || !(value instanceof BaseEntity)) {
			logger.log(Level.WARNING, "Can not convert value: {0}", value);
			return "";
		}
		
		Long id = ((BaseEntity) value).getId();
		return id != null ? id.toString() : null;
	}

}
