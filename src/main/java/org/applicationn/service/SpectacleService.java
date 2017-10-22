package org.applicationn.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.transaction.Transactional;

import org.applicationn.domain.SpectacleEntity;

@Named
public class SpectacleService extends BaseService<SpectacleEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public SpectacleService(){
        super(SpectacleEntity.class);
    }
    
    @Transactional
    public List<SpectacleEntity> findAllSpectacleEntities() {
        
        return entityManager.createQuery("SELECT o FROM Spectacle o ", SpectacleEntity.class).getResultList();
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Spectacle o", Long.class).getSingleResult();
    }
    
    @Override
    protected void handleDependenciesBeforeDelete(SpectacleEntity spectacle) {

        /* This is called before a Spectacle is deleted. Place here all the
           steps to cut dependencies to other entities */
        
    }

}
