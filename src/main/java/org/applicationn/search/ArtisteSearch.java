package org.applicationn.search;

import javax.json.JsonObject;
import java.util.List;

import org.applicationn.domain.ArtisteEntity;
import org.applicationn.search.criteria.Filter;
import org.applicationn.search.criteria.artiste.NameFilter;
import org.applicationn.service.RechercheService;

public class ArtisteSearch extends Search<ArtisteEntity>
{
	public ArtisteSearch(RechercheService service)
	{
		super(service);
	}

	@Override
	List<ArtisteEntity> findAll()
	{
		return service.findAllArtisteEntities();
	}

	@Override
	List<ArtisteEntity> findAllMatching(String condition)
	{
		return service.findAllArtisteEntitiesMatching(condition);
	}

	@Override
	Filter createFilter(JsonObject o)
	{
		switch(o.getString("id"))
		{
			case NameFilter.ID:
			{
				return new NameFilter(o.getString("name"));
			}

			default:
			{
				return null;
			}
		}
	}
}
