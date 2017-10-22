package org.applicationn.web.generic;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.applicationn.domain.BaseEntity;
import org.applicationn.service.BaseService;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class GenericLazyDataModel<T extends BaseEntity> extends LazyDataModel<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private List<T> data;
    private BaseService<T> service;
    
    public GenericLazyDataModel(BaseService<T> service) {
        this.service = service;
    }
    
    @Override
    public T getRowData(String rowKey) {
        for(T entry : data) {
            if(entry.getId().toString().equals(rowKey))
                return entry;
        }
        return null;
    }

    @Override
    public Object getRowKey(T entry) {
        return entry.getId();
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        
        data = service.findEntriesPagedAndFilteredAndSorted(first, pageSize, sortField, sortOrder, filters);
        
        this.setRowCount((int) service.countAllEntries());

        return data;
    }
}
                    
