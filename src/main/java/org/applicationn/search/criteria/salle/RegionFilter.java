package org.applicationn.search.criteria.salle;

import javax.json.JsonString;
import javax.json.JsonValue;

import org.applicationn.search.exception.InvalidFilterException;

public class RegionFilter extends SalleFilter
{
	public static final String ID = "region";

	private RegionFilter(Region region)
	{
		super(ID);

		setVar("region", region.getRegion());
	}

	@Override
	public String condition()
	{
		return attribute("ville") + " = " + variable("region");
	}

	public static RegionFilter parse(JsonValue json) throws InvalidFilterException
	{
		String value = ((JsonString) json).getString();

		if(value == null)
		{
			throw new InvalidFilterException(ID);
		}

		Region region = Region.get(value);

		if(region == null)
		{
			throw new InvalidFilterException(ID);
		}

		return new RegionFilter(region);
	}
}
