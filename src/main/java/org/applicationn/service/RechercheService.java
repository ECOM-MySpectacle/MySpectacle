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
		return findAllEntities(SpectacleEntity.class, "SELECT o FROM Spectacle o");
	}

	public List<SpectacleEntity> findAllSpectacleEntitiesMatching(String criteria)
	{
		return findAllEntities(SpectacleEntity.class, "SELECT o FROM Spectacle o WHERE " + criteria);
	}

	public List<SalleEntity> findAllSalleEntities()
	{
		return findAllEntities(SalleEntity.class, "SELECT o FROM Salle o");
	}

	public List<SalleEntity> findAllSalleEntitiesMatching(String criteria)
	{
		return findAllEntities(SalleEntity.class, "SELECT o FROM Salle o WHERE " + criteria);
	}

	public List<ArtisteEntity> findAllArtisteEntities()
	{
		return findAllEntities(ArtisteEntity.class, "SELECT o FROM Artiste o");
	}

	public List<ArtisteEntity> findAllArtisteEntitiesMatching(String criteria)
	{
		return findAllEntities(ArtisteEntity.class, "SELECT o FROM Artiste o WHERE " + criteria);
	}

	public List<RepresentationEntity> findAllRepresentationEntities()
	{
		return findAllEntities(RepresentationEntity.class, "SELECT o FROM Representation o");
	}

	public List<RepresentationEntity> findAllRepresentationEntitiesMatching(String criteria)
	{
		return findAllEntities(RepresentationEntity.class, "SELECT o FROM Representation o WHERE " + criteria);
	}
}
