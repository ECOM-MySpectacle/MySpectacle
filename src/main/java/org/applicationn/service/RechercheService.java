package org.applicationn.service;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

import org.applicationn.domain.*;
import org.applicationn.search.SearchParameters;
import org.applicationn.search.SearchResult;

@Named
public class RechercheService implements Serializable
{
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	private <T extends BaseEntity> SearchResult<T> findAllEntities(Class<T> classOfT, SearchParameters params, String query)
	{
		List<T> entities = entityManager.createQuery(query, classOfT).getResultList();

		return new SearchResult<>(params, entities);
	}

	public SearchResult<SpectacleEntity> findAllSpectacleEntities(SearchParameters params)
	{
		return findAllEntities(SpectacleEntity.class, params, "SELECT sp FROM Spectacle sp");
	}

	public SearchResult<SpectacleEntity> findAllSpectacleEntitiesMatching(SearchParameters params, String criteria)
	{
		return findAllEntities(SpectacleEntity.class, params, "SELECT sp FROM Spectacle sp WHERE " + criteria);
	}

	public SearchResult<SalleEntity> findAllSalleEntities(SearchParameters params)
	{
		return findAllEntities(SalleEntity.class, params, "SELECT sa FROM Salle sa");
	}

	public SearchResult<SalleEntity> findAllSalleEntitiesMatching(SearchParameters params, String criteria)
	{
		return findAllEntities(SalleEntity.class, params, "SELECT sa FROM Salle sa WHERE " + criteria);
	}

	public SearchResult<ArtisteEntity> findAllArtisteEntities(SearchParameters params)
	{
		return findAllEntities(ArtisteEntity.class, params, "SELECT a FROM Artiste a");
	}

	public SearchResult<ArtisteEntity> findAllArtisteEntitiesMatching(SearchParameters params, String criteria)
	{
		return findAllEntities(ArtisteEntity.class, params, "SELECT a FROM Artiste a WHERE " + criteria);
	}

	public SearchResult<RepresentationEntity> findAllRepresentationEntities(SearchParameters params)
	{
		return findAllEntities(RepresentationEntity.class, params, "SELECT r FROM Representation r");
	}

	public SearchResult<RepresentationEntity> findAllRepresentationEntitiesMatching(SearchParameters params, String criteria)
	{
		return findAllEntities(RepresentationEntity.class, params, "SELECT r FROM Representation r LEFT JOIN r.salle sa LEFT JOIN r.spectacle sp LEFT JOIN sp.artistess a WHERE " + criteria);
	}
}
