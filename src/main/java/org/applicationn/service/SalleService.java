package org.applicationn.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.transaction.Transactional;

import org.applicationn.domain.SalleEntity;

@Named
public class SalleService extends BaseService<SalleEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public SalleService(){
        super(SalleEntity.class);
    }
    
    @Transactional
    public List<SalleEntity> findAllSalleEntities() {
        
        return entityManager.createQuery("SELECT o FROM Salle o ", SalleEntity.class).getResultList();
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Salle o", Long.class).getSingleResult();
    }
    
    @Override
    protected void handleDependenciesBeforeDelete(SalleEntity salle) {

        /* This is called before a Salle is deleted. Place here all the
           steps to cut dependencies to other entities */
        
    }

}
