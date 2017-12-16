package org.applicationn.search.criteria.spectacle;

import javax.json.JsonArray;
import javax.json.JsonString;
import javax.json.JsonValue;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.IntStream;

import org.applicationn.domain.SpectacleGenre;
import org.applicationn.search.criteria.InvalidFilterException;
import org.applicationn.search.criteria.MalformedFilterException;

public class GenreFilter extends SpectacleFilter
{
	public static final String ID = "genre";

	public GenreFilter(String[] genres) throws InvalidFilterException
	{
		super(ID);

		if(genres == null || genres.length == 0)
		{
			throw new InvalidFilterException(ID);
		}

		EnumSet<SpectacleGenre> e = EnumSet.noneOf(SpectacleGenre.class);

		for(String genre : genres)
		{
			SpectacleGenre sg = Arrays.stream(SpectacleGenre.values()).filter(s -> s.toString().equalsIgnoreCase(genre)).findFirst().orElse(null);

			if(sg == null)
			{
				throw new InvalidFilterException(ID);
			}

			e.add(sg);
		}

		setVar("genres", e);
	}

	public GenreFilter(String multigenres) throws InvalidFilterException
	{
		super(ID);

		if(multigenres == null || multigenres.isEmpty())
		{
			throw new InvalidFilterException(ID);
		}

		EnumSet<SpectacleGenre> e = get(multigenres);

		if(e == null)
		{
			throw new InvalidFilterException(ID);
		}

		setVar("genres", e);
	}

	private static EnumSet<SpectacleGenre> get(String multigenres)
	{
		switch(multigenres)
		{
			case "Concerts":
			{
				return EnumSet.of(SpectacleGenre.CONCERT);
			}

			case "Théâtre & Humour":
			{
				return EnumSet.of(SpectacleGenre.THEATRE, SpectacleGenre.HUMOUR);
			}

			case "Sports":
			{
				return EnumSet.of(SpectacleGenre.SPORT);
			}

			case "Spectacle, cabarets, cirques":
			{
				return EnumSet.of(SpectacleGenre.SPECTACLE, SpectacleGenre.CABARET, SpectacleGenre.CIRQUE);
			}

			case "Musées, expos, monuments":
			{
				return EnumSet.of(SpectacleGenre.MUSEE, SpectacleGenre.EXPOSITION, SpectacleGenre.MONUMENT);
			}

			case "Parcs, salons, ciné":
			{
				return EnumSet.of(SpectacleGenre.PARC, SpectacleGenre.SALON, SpectacleGenre.CINEMA);
			}

			case "Festivals":
			{
				return EnumSet.of(SpectacleGenre.FESTIVAL);
			}

			case "Classique, opéra, danse":
			{
				return EnumSet.of(SpectacleGenre.OPERA);
			}

			case "Spectacles pour enfants":
			{
				return EnumSet.of(SpectacleGenre.SPECTACLEENFANT);
			}

			case "Activités de Loisirs":
			{
				return EnumSet.of(SpectacleGenre.LOISIR);
			}

			default:
			{
				return null;
			}
		}
	}

	public static GenreFilter parse(JsonValue json) throws InvalidFilterException, MalformedFilterException
	{
		JsonValue.ValueType type = json.getValueType();

		if(type == JsonValue.ValueType.ARRAY)
		{
			JsonArray a = ((JsonArray) json);
			String[] genres = IntStream.range(0, a.size()).mapToObj(a::getString).toArray(String[]::new);

			return new GenreFilter(genres);
		}

		if(type == JsonValue.ValueType.STRING)
		{
			return new GenreFilter(((JsonString) json).getString());
		}

		throw new MalformedFilterException(ID);
	}

	@Override
	public String condition()
	{
		return attribute("genre") + " IN (" + variable("genres") + ")";
	}
}
