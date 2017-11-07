package org.applicationn.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.persistence.PersistenceUnitUtil;
import javax.transaction.Transactional;

import org.applicationn.domain.ArtisteEntity;
import org.applicationn.domain.SpectacleEntity;

@Named
public class SpectacleService extends BaseService<SpectacleEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public SpectacleService(){
        super(SpectacleEntity.class);
    }
    
    @Transactional
    public List<SpectacleEntity> findAllSpectacleEntities() {
        
        return entityManager.createQuery("SELECT o FROM Spectacle o LEFT JOIN FETCH o.image", SpectacleEntity.class).getResultList();
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

    @Transactional
    public List<SpectacleEntity> findAvailableSpectacless(ArtisteEntity artiste) {
        return entityManager.createQuery("SELECT o FROM Spectacle o where o.id not in (select o.id from Spectacle o join o.artistess p where p = :p)", SpectacleEntity.class).setParameter("p", artiste).getResultList();
    }

    @Transactional
    public List<SpectacleEntity> findSpectaclessByArtistes(ArtisteEntity artiste) {
        return entityManager.createQuery("SELECT o FROM Spectacle o where o.id in (select o.id from Spectacle o join o.artistess p where p = :p)", SpectacleEntity.class).setParameter("p", artiste).getResultList();
    }

    @Transactional
    public SpectacleEntity fetchArtistess(SpectacleEntity spectacle) {
        spectacle = find(spectacle.getId());
        spectacle.getArtistess().size();
        return spectacle;
    }
    
    @Transactional
    public SpectacleEntity lazilyLoadImageToSpectacle(SpectacleEntity spectacle) {
        PersistenceUnitUtil u = entityManager.getEntityManagerFactory().getPersistenceUnitUtil();
        if (!u.isLoaded(spectacle, "image") && spectacle.getId() != null) {
            spectacle = find(spectacle.getId());
            spectacle.getImage().getId();
        }
        return spectacle;
    }
    
}
