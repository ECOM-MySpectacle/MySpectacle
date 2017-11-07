package org.applicationn.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.persistence.PersistenceUnitUtil;
import javax.transaction.Transactional;

import org.applicationn.domain.ArtisteEntity;
import org.applicationn.domain.SpectacleEntity;

@Named
public class ArtisteService extends BaseService<ArtisteEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public ArtisteService(){
        super(ArtisteEntity.class);
    }
    
    @Transactional
    public List<ArtisteEntity> findAllArtisteEntities() {
        
        return entityManager.createQuery("SELECT o FROM Artiste o LEFT JOIN FETCH o.image", ArtisteEntity.class).getResultList();
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

    @Transactional
    public List<ArtisteEntity> findAvailableArtistess(SpectacleEntity spectacle) {
        return entityManager.createQuery("SELECT o FROM Artiste o where o.id not in (select o.id from Artiste o join o.spectacless p where p = :p)", ArtisteEntity.class).setParameter("p", spectacle).getResultList();
    }

    @Transactional
    public List<ArtisteEntity> findArtistessBySpectacles(SpectacleEntity spectacle) {
        return entityManager.createQuery("SELECT o FROM Artiste o where o.id in (select o.id from Artiste o join o.spectacless p where p = :p)", ArtisteEntity.class).setParameter("p", spectacle).getResultList();
    }

    @Transactional
    public ArtisteEntity fetchSpectacless(ArtisteEntity artiste) {
        artiste = find(artiste.getId());
        artiste.getSpectacless().size();
        return artiste;
    }
    
    @Transactional
    public ArtisteEntity lazilyLoadImageToArtiste(ArtisteEntity artiste) {
        PersistenceUnitUtil u = entityManager.getEntityManagerFactory().getPersistenceUnitUtil();
        if (!u.isLoaded(artiste, "image") && artiste.getId() != null) {
            artiste = find(artiste.getId());
            artiste.getImage().getId();
        }
        return artiste;
    }
    
}
