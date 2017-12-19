package org.applicationn.search.criteria.salle;

import javax.json.JsonArray;
import javax.json.JsonValue;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.IntStream;

import org.applicationn.search.exception.InvalidFilterException;

public class RegionFilter extends SalleFilter
{
	public static final String ID = "region";

	private RegionFilter(Set<Region> regions)
	{
		super(ID);

		setVar("regions", regions);
	}

	@Override
	public String condition()
	{
		return attribute("ville") + " IN " + variable("regions");
	}

	public static RegionFilter parse(JsonValue json) throws InvalidFilterException
	{
		JsonArray a = (JsonArray) json;
		String[] regions = IntStream.range(0, a.size()).mapToObj(a::getString).toArray(String[]::new);

		if(regions == null)
		{
			throw new InvalidFilterException(ID);
		}

		if(regions.length == 0)
		{
			return null;
		}

		Set<Region> e = EnumSet.noneOf(Region.class);

		for(String region : regions)
		{
			Region rg = Region.get(region);

			if(rg == null)
			{
				throw new InvalidFilterException(ID);
			}

			e.add(rg);
		}

		return new RegionFilter(e);
	}
}
