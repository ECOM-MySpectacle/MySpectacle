package org.applicationn.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.transaction.Transactional;

import org.applicationn.domain.ReservationEntity;

@Named
public class ReservationService extends BaseService<ReservationEntity> implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public ReservationService(){
        super(ReservationEntity.class);
    }
    
    @Transactional
    public List<ReservationEntity> findAllReservationEntities() {
        
        return entityManager.createQuery("SELECT o FROM Reservation o ", ReservationEntity.class).getResultList();
    }
    
    @Override
    @Transactional
    public long countAllEntries() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Reservation o", Long.class).getSingleResult();
    }
    
    @Override
    protected void handleDependenciesBeforeDelete(ReservationEntity reservation) {

        /* This is called before a Reservation is deleted. Place here all the
           steps to cut dependencies to other entities */
        
    }

}
