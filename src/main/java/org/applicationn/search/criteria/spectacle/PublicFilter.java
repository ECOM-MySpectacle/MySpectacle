package org.applicationn.search.criteria.spectacle;

import java.util.Arrays;
import java.util.StringJoiner;

import org.applicationn.domain.SpectaclePublicc;
import org.applicationn.search.exception.InvalidFilterException;

public class PublicFilter extends SpectacleFilter
{
	public static final String ID = "public";

	public PublicFilter(String[] publc) throws InvalidFilterException
	{
		super(ID);

		if(publc == null || publc.length == 0)
		{
			throw new InvalidFilterException(ID);
		}

		int k = 0;

		for(String genre : publc)
		{
			SpectaclePublicc sp = Arrays.stream(SpectaclePublicc.values()).filter(s -> s.toString().equalsIgnoreCase(genre)).findFirst().orElse(null);

			if(sp == null)
			{
				throw new InvalidFilterException(ID);
			}

			setVar("public_" + k++, sp);
		}
	}

	@Override
	public String condition()
	{
		String publicAttr = attribute("publicc");
		StringJoiner condition = new StringJoiner(" AND ");

		for(int k = 0, max = getVars().size(); k < max; k++)
		{
			condition.add(variable("public_" + k) + " MEMBER OF " + publicAttr);
		}

		return condition.toString();
	}
}
