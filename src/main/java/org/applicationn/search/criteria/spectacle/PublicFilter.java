package org.applicationn.search.criteria.spectacle;

import javax.json.JsonArray;
import javax.json.JsonValue;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.IntStream;

import org.applicationn.domain.SpectaclePublicc;
import org.applicationn.search.criteria.Filter;
import org.applicationn.search.exception.InvalidFilterException;

public class PublicFilter extends SpectacleFilter
{
	public static final String ID = "public";

	private PublicFilter(Set<SpectaclePublicc> publc)
	{
		super(ID);

		int k = 0;

		for(SpectaclePublicc sp : publc)
		{
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

	public static Filter parse(JsonValue value) throws InvalidFilterException
	{
		JsonArray a = (JsonArray) value;
		String[] publcc = IntStream.range(0, a.size()).mapToObj(a::getString).toArray(String[]::new);

		if(publcc == null)
		{
			throw new InvalidFilterException(ID);
		}

		if(publcc.length == 0)
		{
			return null;
		}

		Set<SpectaclePublicc> e = EnumSet.noneOf(SpectaclePublicc.class);

		for(String p : publcc)
		{
			SpectaclePublicc sp = Arrays.stream(SpectaclePublicc.values()).filter(s -> s.toString().equalsIgnoreCase(p)).findFirst().orElse(null);

			if(sp == null)
			{
				throw new InvalidFilterException(ID);
			}

			e.add(sp);
		}

		return new PublicFilter(e);
	}
}
