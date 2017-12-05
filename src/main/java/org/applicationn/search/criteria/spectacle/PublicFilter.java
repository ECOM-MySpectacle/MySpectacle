package org.applicationn.search.criteria.spectacle;

import java.util.Arrays;

import org.applicationn.domain.SpectaclePublicc;
import org.applicationn.search.criteria.InvalidFilterException;

public class PublicFilter extends SpectacleFilter
{
	public static final String ID = "sp_public";
	private final String[] publc;

	public PublicFilter(String[] publc) throws InvalidFilterException
	{
		super(ID);

		if(publc == null || publc.length == 0)
		{
			throw new InvalidFilterException(ID);
		}

		for(String p : publc)
		{
			if(Arrays.stream(SpectaclePublicc.values()).map(Enum::toString).noneMatch(s -> s.equalsIgnoreCase(p)))
			{
				throw new InvalidFilterException(ID);
			}
		}

		this.publc = publc;
	}

	@Override
	public String condition()
	{
		return attribute("publicc") + " IN (" + String.join(",", publc) + ")";
	}
}
