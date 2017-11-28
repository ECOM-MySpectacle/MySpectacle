package org.applicationn.search;

import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.*;

import org.applicationn.domain.BaseEntity;
import org.applicationn.search.criteria.Filter;
import org.applicationn.search.criteria.UnknownFilterException;
import org.applicationn.service.RechercheService;

public abstract class Search<T extends BaseEntity>
{
	private final Map<String, List<Filter>> filters = new HashMap<>();
	protected final RechercheService service;

	Search(RechercheService service)
	{
		this.service = service;
	}

	abstract SearchResult<T> findAll(SearchParameters params);

	abstract SearchResult<T> findAllMatching(SearchParameters params, String condition);

	abstract Filter createFilter(JsonObject json) throws UnknownFilterException, JsonException;

	public final void createFilters(JsonArray a) throws UnknownFilterException, JsonException
	{
		if(a != null && !a.isEmpty())
		{
			for(JsonValue v : a)
			{
				if(v.getValueType() == JsonValue.ValueType.OBJECT)
				{
					Filter filter = createFilter((JsonObject) v);

					if(filter != null)
					{
						addFilter(filter);
					}
				}
			}
		}
	}

	public final void addFilter(Filter filter)
	{
		String id = filter.getId();
		List<Filter> l = filters.get(filter.getId());

		if(l == null)
		{
			l = new LinkedList<>();

			filters.put(id, l);
		}

		l.add(filter);
	}

	public final void removeFilter(String id)
	{
		filters.remove(id);
	}

	public final List<Filter> getFilter(String id)
	{
		return filters.get(id);
	}

	private String flattenFilters(List<Filter> f)
	{
		StringJoiner condition = new StringJoiner(") OR (", "(", ")");

		f.forEach(filter -> condition.add(filter.condition()));

		return condition.toString();
	}

	public final SearchResult<T> find(SearchParameters params)
	{
		if(filters.isEmpty())
		{
			return findAll(params);
		}

		StringJoiner condition = new StringJoiner(") AND (", "(", ")");
		filters.values().stream().map(this::flattenFilters).forEach(condition::add);

		return condition.length() == 0 ? SearchResult.EMPTY : findAllMatching(params, condition.toString());
	}
}
