package org.applicationn.search;

import javax.json.JsonObject;
import java.util.List;

import org.applicationn.domain.RepresentationEntity;
import org.applicationn.search.criteria.Filter;
import org.applicationn.service.RechercheService;

public class RepresentationSearch extends Search<RepresentationEntity>
{
	public RepresentationSearch(RechercheService service)
	{
		super(service);
	}

	@Override
	List<RepresentationEntity> findAll()
	{
		return service.findAllRepresentationEntities();
	}

	@Override
	List<RepresentationEntity> findAllMatching(String condition)
	{
		return service.findAllRepresentationEntitiesMatching(condition);
	}

	@Override
	Filter createFilter(JsonObject o)
	{
		switch(o.getString("id"))
		{
			default:
			{
				return null;
			}
		}
	}
}
