package org.applicationn.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.transaction.Transactional;

import org.applicationn.domain.SalleEntity;
import org.applicationn.domain.security.UserEntity;

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
        
        this.cutAllSalleRepresentationsAssignments(salle);
        
    }

    // Remove all assignments from all representation a salle. Called before delete a salle.
    @Transactional
    private void cutAllSalleRepresentationsAssignments(SalleEntity salle) {
        entityManager
                .createQuery("UPDATE Representation c SET c.salle = NULL WHERE c.salle = :p")
                .setParameter("p", salle).executeUpdate();
    }
    
    @Transactional
    public List<SalleEntity> findAvailableSalles(UserEntity user) {
        return entityManager.createQuery("SELECT o FROM Salle o WHERE o.gestionnaire IS NULL", SalleEntity.class).getResultList();
    }

    @Transactional
    public List<SalleEntity> findSallesByGestionnaire(UserEntity user) {
        return entityManager.createQuery("SELECT o FROM Salle o WHERE o.gestionnaire = :user", SalleEntity.class).setParameter("user", user).getResultList();
    }

}
