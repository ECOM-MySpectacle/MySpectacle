package org.applicationn.search.criteria.spectacle;

import javax.json.JsonArray;
import javax.json.JsonValue;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.IntStream;

import org.applicationn.domain.SpectacleGenre;
import org.applicationn.search.exception.InvalidFilterException;

public class GenreFilter extends SpectacleFilter
{
	public static final String ID = "genre";

	private GenreFilter(Set<SpectacleGenre> genres)
	{
		super(ID);

		setVar("genres", genres);
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

	@Override
	public String condition()
	{
		return attribute("genre") + " IN " + variable("genres");
	}

	public static GenreFilter parse(JsonValue value) throws InvalidFilterException
	{
		JsonArray a = (JsonArray) value;
		String[] genres = IntStream.range(0, a.size()).mapToObj(a::getString).toArray(String[]::new);

		if(genres == null)
		{
			throw new InvalidFilterException(ID);
		}

		if(genres.length == 0)
		{
			return null;
		}

		Set<SpectacleGenre> e = EnumSet.noneOf(SpectacleGenre.class);

		for(String genre : genres)
		{
			Set<SpectacleGenre> f = get(genre);

			if(f == null)
			{
				SpectacleGenre sg = Arrays.stream(SpectacleGenre.values()).filter(s -> s.toString().equalsIgnoreCase(genre)).findFirst().orElse(null);

				if(sg == null)
				{
					throw new InvalidFilterException(ID);
				}

				e.add(sg);
			}
			else
			{
				e.addAll(f);
			}
		}

		return new GenreFilter(e);
	}
}
