package org.applicationn.service;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

import org.applicationn.domain.*;
import org.applicationn.search.SearchQuery;
import org.applicationn.search.SearchResult;

@Named
public class RechercheService implements Serializable
{
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	private <T extends BaseEntity> SearchResult<T> findAllEntities(Class<T> classOfT, SearchQuery query, String sql)
	{
		TypedQuery<T> statement = entityManager.createQuery(sql, classOfT);

		if(query.vars != null && !query.vars.isEmpty())
		{
			query.vars.forEach(statement::setParameter);
		}

		List<T> entities = statement.getResultList();

		return new SearchResult<>(query.params, entities);
	}

	public SearchResult<SpectacleEntity> findAllSpectacleEntities(SearchQuery query)
	{
		return findAllEntities(SpectacleEntity.class, query, "SELECT sp FROM Spectacle sp LEFT JOIN FETCH sp.image");
	}

	public SearchResult<SpectacleEntity> findAllSpectacleEntitiesMatching(SearchQuery query)
	{
		return findAllEntities(SpectacleEntity.class, query, "SELECT sp FROM Spectacle sp LEFT JOIN FETCH sp.image WHERE " + query.condition);
	}

	public SearchResult<SalleEntity> findAllSalleEntities(SearchQuery query)
	{
		return findAllEntities(SalleEntity.class, query, "SELECT sa FROM Salle sa");
	}

	public SearchResult<SalleEntity> findAllSalleEntitiesMatching(SearchQuery query)
	{
		return findAllEntities(SalleEntity.class, query, "SELECT sa FROM Salle sa WHERE " + query.condition);
	}

	public SearchResult<ArtisteEntity> findAllArtisteEntities(SearchQuery query)
	{
		return findAllEntities(ArtisteEntity.class, query, "SELECT a FROM Artiste a");
	}

	public SearchResult<ArtisteEntity> findAllArtisteEntitiesMatching(SearchQuery query)
	{
		return findAllEntities(ArtisteEntity.class, query, "SELECT a FROM Artiste a WHERE " + query.condition);
	}

	public SearchResult<RepresentationEntity> findAllRepresentationEntities(SearchQuery query)
	{
		return findAllEntities(RepresentationEntity.class, query, "SELECT r FROM Representation r LEFT JOIN r.salle sa LEFT JOIN r.spectacle sp LEFT JOIN FETCH r.spectacle.image");
	}

	public SearchResult<RepresentationEntity> findAllRepresentationEntitiesMatching(SearchQuery query)
	{
		return findAllEntities(RepresentationEntity.class, query, "SELECT r FROM Representation r LEFT JOIN r.salle sa LEFT JOIN r.spectacle sp LEFT JOIN FETCH r.spectacle.image WHERE " + query.condition);
	}
}
