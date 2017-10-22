package org.applicationn.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.transaction.Transactional;

import org.applicationn.domain.PanierEntity;

@Named
public class PanierService extends BaseService<PanierEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public PanierService(){
        super(PanierEntity.class);
    }
    
    @Transactional
    public List<PanierEntity> findAllPanierEntities() {
        
        return entityManager.createQuery("SELECT o FROM Panier o ", PanierEntity.class).getResultList();
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Panier o", Long.class).getSingleResult();
    }
    
    @Override
    protected void handleDependenciesBeforeDelete(PanierEntity panier) {

        /* This is called before a Panier is deleted. Place here all the
           steps to cut dependencies to other entities */
        
    }

}
