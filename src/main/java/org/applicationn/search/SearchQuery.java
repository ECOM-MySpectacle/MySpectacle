package org.applicationn.search;

import java.util.Map;

/**
 * This class holds all required parameters to execute a JPQL query.
 */
public class SearchQuery
{
	public final SearchParameters params;
	public final String condition;
	public final Map<String, Object> vars;

	SearchQuery(SearchParameters params, String condition, Map<String, Object> vars)
	{
		this.params = params;
		this.condition = condition;
		this.vars = vars;
	}

	/**
	 * Returns a {@code SearchQuery} without any filter.
	 *
	 * @param params the query parameters
	 * @return A filter-less query
	 */
	public static SearchQuery empty(SearchParameters params)
	{
		return new SearchQuery(params, null, null);
	}
}
