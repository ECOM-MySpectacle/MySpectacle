package org.applicationn.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.transaction.Transactional;

import org.applicationn.domain.RepresentationEntity;

@Named
public class RepresentationService extends BaseService<RepresentationEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public RepresentationService(){
        super(RepresentationEntity.class);
    }
    
    @Transactional
    public List<RepresentationEntity> findAllRepresentationEntities() {
        
        return entityManager.createQuery("SELECT o FROM Representation o ", RepresentationEntity.class).getResultList();
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Representation o", Long.class).getSingleResult();
    }
    
    @Override
    protected void handleDependenciesBeforeDelete(RepresentationEntity representation) {

        /* This is called before a Representation is deleted. Place here all the
           steps to cut dependencies to other entities */
        
    }

}
