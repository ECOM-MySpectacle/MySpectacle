package org.applicationn.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.applicationn.domain.BaseEntity;

/**
 * Holds the result of a search query.
 *
 * @param <T> The type of elements queried
 */
public class SearchResult<T extends BaseEntity>
{
	/**
	 * Empty result
	 */
	@SuppressWarnings("rawtypes")
	static final SearchResult EMPTY = new SearchResult<>(new SearchParameters(), Collections.emptyList());
	private final int pages, total;
	private final List<T> entities;

	public SearchResult(SearchParameters params, List<T> result)
	{
		total = result.size();
		int page = params.page, perPage = params.perPage;
		pages = (int) Math.ceil((double) total / (double) perPage);

		if(page < 1 || page > pages)
		{
			entities = Collections.emptyList();
		}
		else
		{
			int start = (page - 1) * perPage, max = Math.min(total, start + perPage);

			entities = new ArrayList<>(result.subList(start, max));
		}
	}

	public int getPages()
	{
		return pages;
	}

	public int getTotal()
	{
		return total;
	}

	public List<T> getEntities()
	{
		return entities;
	}
}
