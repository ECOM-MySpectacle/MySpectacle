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
		TypedQuery<T> statement = entityManager.createQuery(query.condition == null ? sql : sql + " WHERE " + query.condition, classOfT);

		if(query.vars != null && !query.vars.isEmpty())
		{
			query.vars.forEach(statement::setParameter);
		}

		List<T> entities = statement.getResultList();

		return new SearchResult<>(query.params, entities);
	}

	public SearchResult<SpectacleEntity> findSpectacleEntities(SearchQuery query)
	{
		return findAllEntities(SpectacleEntity.class, query, "SELECT sp FROM Representation r LEFT JOIN r.spectacle sp LEFT JOIN r.salle sa");
	}

	public SearchResult<SalleEntity> findSalleEntities(SearchQuery query)
	{
		return findAllEntities(SalleEntity.class, query, "SELECT sa FROM Salle sa");
	}

	public SearchResult<ArtisteEntity> findArtisteEntities(SearchQuery query)
	{
		return findAllEntities(ArtisteEntity.class, query, "SELECT a FROM Artiste a");
	}

	public SearchResult<RepresentationEntity> findRepresentationEntities(SearchQuery query)
	{
		return findAllEntities(RepresentationEntity.class, query, "SELECT r FROM Representation r LEFT JOIN r.salle sa LEFT JOIN r.spectacle sp");
	}
}
