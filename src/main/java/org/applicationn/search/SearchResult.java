package org.applicationn.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.applicationn.domain.BaseEntity;

public class SearchResult<T extends BaseEntity>
{
	static final SearchResult EMPTY = new SearchResult(new SearchParameters(), Collections.EMPTY_LIST);
	private final int pages, total;
	private final List<T> entities;

	public SearchResult(SearchParameters params, List<T> result)
	{
		total = result.size();
		int page = params.page, perPage = params.perPage;
		pages = total / perPage + 1;

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

	public List<T> getEntities()
	{
		return entities;
	}

	public int getTotal()
	{
		return total;
	}

	public int getPages()
	{
		return pages;
	}
}
