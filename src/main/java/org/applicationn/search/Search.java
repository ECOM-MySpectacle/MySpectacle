package org.applicationn.search;

import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.*;

import org.applicationn.domain.BaseEntity;
import org.applicationn.search.criteria.Filter;
import org.applicationn.search.criteria.InvalidFilterException;
import org.applicationn.search.criteria.MalformedFilterException;
import org.applicationn.search.criteria.UnknownFilterException;
import org.applicationn.service.RechercheService;

/**
 * Base class for searches.<br/>
 * Each search has filters which are predicates that an entity must satisfy in order to be returned.<br/>
 * If multiple filters of the same identifier are given, they are OR'd together.
 *
 * @param <T> The type of elements queried
 */
public abstract class Search<T extends BaseEntity>
{
	/**
	 * The map of list of filters.<br/>
	 */
	private final Map<String, List<Filter>> filters = new HashMap<>();
	protected final RechercheService service;

	Search(RechercheService service)
	{
		this.service = service;
	}

	/**
	 * Returns all entities regardless of filters.
	 *
	 * @param query The search params
	 * @return All entities
	 */
	abstract SearchResult<T> findAll(SearchQuery query);

	/**
	 * Returns all entities matching the filters.
	 *
	 * @param query The search query (contains the search params, filters and vars)
	 * @return All matching entities
	 */
	abstract SearchResult<T> findAllMatching(SearchQuery query);

	/**
	 * Creates a {@link Filter} from a JSON object.
	 *
	 * @param key  the key
	 * @param json the JSON object representing the filter
	 * @return The filter
	 * @throws InvalidFilterException if the filter is invalid
	 * @throws JsonException          if a JSON exception occurs
	 */
	abstract Filter createFilter(String key, JsonValue json) throws InvalidFilterException, JsonException;

	public final void createFilters(JsonObject o) throws UnknownFilterException, InvalidFilterException, MalformedFilterException
	{
		if(o != null && !o.isEmpty())
		{
			for(Map.Entry<String, JsonValue> entry : o.entrySet())
			{
				String key = entry.getKey();
				Filter filter;

				try
				{
					filter = createFilter(key, entry.getValue());
				}
				catch(JsonException e)
				{
					throw new MalformedFilterException(key);
				}

				if(filter == null)
				{
					throw new UnknownFilterException(key);
				}

				addFilter(filter);
			}
		}
	}

	/**
	 * Adds a filter to the filter list.
	 *
	 * @param filter the filter
	 */
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

	/**
	 * Removed a filter to the filter list.
	 *
	 * @param id the filter id
	 */
	public final void removeFilter(String id)
	{
		filters.remove(id);
	}

	/**
	 * Gets the list of filters by id.
	 *
	 * @param id the filter identifier
	 */
	public final List<Filter> getFilter(String id)
	{
		return filters.get(id);
	}

	private String flattenFilters(List<Filter> f, Map<String, Object> vars)
	{
		StringJoiner condition = new StringJoiner(") OR (", "(", ")");

		f.forEach(filter ->
		{
			condition.add(filter.condition());
			vars.putAll(filter.getVars());
		});

		return condition.toString();
	}

	public final SearchResult<T> find(SearchParameters params)
	{
		if(filters.isEmpty())
		{
			return findAll(SearchQuery.withoutCondition(params));
		}

		StringJoiner condition = new StringJoiner(") AND (", "(", ")");
		Map<String, Object> vars = new HashMap<>();
		filters.values().stream().map(l -> flattenFilters(l, vars)).forEach(condition::add);

		return condition.length() == 0 ? SearchResult.EMPTY : findAllMatching(new SearchQuery(params, condition.toString(), vars));
	}
}
