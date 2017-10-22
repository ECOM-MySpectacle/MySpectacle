package org.applicationn.service;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.applicationn.domain.BaseEntity;
import org.primefaces.model.SortOrder;

public class BaseService<T extends BaseEntity> {

    @PersistenceContext
    protected EntityManager entityManager;

    public BaseService() {
    }
    
    private Class<T> type;

    public BaseService(Class<T> type) {
        this.type = type;
    }
    
    public Class<T> getType() {
        return type;
    }
    
    @Transactional
    public T save(T entity) {
        this.entityManager.persist(entity);
        this.entityManager.flush();
        this.entityManager.refresh(entity);
        return entity;
    }

    @Transactional
    public T update(T entity) {
        return this.entityManager.merge(entity);
    }
    
    @Transactional
    public T find(Long id) {
        if (id == null) return null;
        return this.entityManager.find(this.type, id);
    }
    
    @Transactional
    public T find(Class<T> type, Object id) {
        if (id == null) return null;
        return this.entityManager.find(type, id);
    }

    @Transactional
    public void delete(T entity) {
        
        handleDependenciesBeforeDelete(entity);
        
        if (this.entityManager.contains(entity)) {
            this.entityManager.remove(entity);
        } else {
            T attached = find(entity.getId());
            this.entityManager.remove(attached);
        }
        
        this.entityManager.flush();
    }
    
    /**
     * This method is called to handle dependend entities before delete an entity
     * @param entity
     */
    protected void handleDependenciesBeforeDelete(T entity) {
        // overwrite this method in extending class, if required
        // to remove related entries or to cut dependencies from DB before delete
    }
    
    // This is the central method called by the DataTable
    public List<T> findEntriesPagedAndFilteredAndSorted(int firstResult, int maxResults, String sortField, SortOrder sortOrder, Map<String, Object> columnFilters) throws RuntimeException {
        // overwrite this method in extending class
        throw new UnsupportedOperationException("Method findEntriesPagedAndFilteredAndSorted() not implemented for this entity service.");
    }
    
    public long countAllEntries() {
        // overwrite this method in extending class
        throw new UnsupportedOperationException("Method countAllEntries() not implemented for this entity service.");
    }

}
