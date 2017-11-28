package org.applicationn.service;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

import org.applicationn.domain.*;

@Named
public class RechercheService implements Serializable
{
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	private <T extends BaseEntity> List<T> findAllEntities(Class<T> classOfT, String query)
	{
		return entityManager.createQuery(query, classOfT).getResultList();
	}

	public List<SpectacleEntity> findAllSpectacleEntities()
	{
		return findAllEntities(SpectacleEntity.class, "SELECT sp FROM Spectacle sp");
	}

	public List<SpectacleEntity> findAllSpectacleEntitiesMatching(String criteria)
	{
		return findAllEntities(SpectacleEntity.class, "SELECT sp FROM Spectacle sp WHERE " + criteria);
	}

	public List<SalleEntity> findAllSalleEntities()
	{
		return findAllEntities(SalleEntity.class, "SELECT sa FROM Salle sa");
	}

	public List<SalleEntity> findAllSalleEntitiesMatching(String criteria)
	{
		return findAllEntities(SalleEntity.class, "SELECT sa FROM Salle sa WHERE " + criteria);
	}

	public List<ArtisteEntity> findAllArtisteEntities()
	{
		return findAllEntities(ArtisteEntity.class, "SELECT a FROM Artiste a");
	}

	public List<ArtisteEntity> findAllArtisteEntitiesMatching(String criteria)
	{
		return findAllEntities(ArtisteEntity.class, "SELECT a FROM Artiste a WHERE " + criteria);
	}

	public List<RepresentationEntity> findAllRepresentationEntities()
	{
		return findAllEntities(RepresentationEntity.class, "SELECT r FROM Representation r");
	}

	public List<RepresentationEntity> findAllRepresentationEntitiesMatching(String criteria)
	{
		return findAllEntities(RepresentationEntity.class, "SELECT r FROM Representation r JOIN r.salle sa JOIN r.spectacle sp JOIN sp.artistess a WHERE " + criteria);
	}
}
