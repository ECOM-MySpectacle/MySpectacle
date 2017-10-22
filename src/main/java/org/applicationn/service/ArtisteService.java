package org.applicationn.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.transaction.Transactional;

import org.applicationn.domain.ArtisteEntity;

@Named
public class ArtisteService extends BaseService<ArtisteEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public ArtisteService(){
        super(ArtisteEntity.class);
    }
    
    @Transactional
    public List<ArtisteEntity> findAllArtisteEntities() {
        
        return entityManager.createQuery("SELECT o FROM Artiste o ", ArtisteEntity.class).getResultList();
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Artiste o", Long.class).getSingleResult();
    }
    
    @Override
    protected void handleDependenciesBeforeDelete(ArtisteEntity artiste) {

        /* This is called before a Artiste is deleted. Place here all the
           steps to cut dependencies to other entities */
        
    }

}
