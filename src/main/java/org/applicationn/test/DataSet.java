package org.applicationn.test;

import java.util.*;

import org.applicationn.domain.*;
import org.applicationn.domain.security.UserEntity;
import org.applicationn.service.ArtisteService;
import org.applicationn.service.RepresentationService;
import org.applicationn.service.SalleService;
import org.applicationn.service.SpectacleService;
import org.applicationn.service.security.UserService;

public class DataSet
{
	private ArtisteService artisteService;
	private SpectacleService spectacleService;
	private SalleService salleService;
	private RepresentationService representationService;
	private final UserService userService;

	public DataSet(ArtisteService artisteService, SpectacleService spectacleService, SalleService salleService, RepresentationService representationService, UserService userService)
	{
		this.artisteService = artisteService;
		this.spectacleService = spectacleService;
		this.salleService = salleService;
		this.representationService = representationService;
		this.userService = userService;
	}

	private ArtisteEntity newArtiste(String nom, String bio)
	{
		ArtisteEntity entity = new ArtisteEntity();
		entity.setNom(nom);
		entity.setBio(bio);
		return entity;
	}

	private SalleEntity newSalle(String nom, String adresse, String ville, Integer nbPlacesFosse, Integer nbPlacesBalcon, Integer nbPlacesOrchestre, UserEntity gestionnaire)
	{
		SalleEntity entity = new SalleEntity();
		entity.setNom(nom);
		entity.setAdresse(adresse);
		entity.setVille(ville);
		entity.setNbPlacesFosse(nbPlacesFosse);
		entity.setNbPlacesBalcon(nbPlacesBalcon);
		entity.setNbPlacesOrchestre(nbPlacesOrchestre);
		entity.setGestionnaire(gestionnaire);
		return entity;
	}

	private SpectacleEntity newSpectacle(String nom, SpectacleGenre genre, Set<SpectaclePublicc> publicc, String theme, String description, List<ArtisteEntity> artistess)
	{
		SpectacleEntity entity = new SpectacleEntity();
		entity.setNom(nom);
		entity.setGenre(genre);
		entity.setPublicc(publicc);
		entity.setTheme(theme);
		entity.setDescription(description);
		entity.setArtistess(artistess);
		return entity;
	}

	private RepresentationEntity newRepresentation(Date date, Integer nbPlacesFosseLibres, Integer nbPlacesBalconLibres, Integer nbPlacesOrchestreLibres, SalleEntity salle, SpectacleEntity spectacle)
	{
		RepresentationEntity entity = new RepresentationEntity();
		entity.setDate(date);
		entity.setNbPlacesFosseLibres(nbPlacesFosseLibres);
		entity.setNbPlacesBalconLibres(nbPlacesBalconLibres);
		entity.setNbPlacesOrchestreLibres(nbPlacesOrchestreLibres);
		entity.setSalle(salle);
		entity.setSalle(spectacle);
		return entity;
	}

	private Date getDate(int year, int month, int day)
	{
		Calendar calendar = Calendar.getInstance();

		calendar.set(year, month, day);

		return calendar.getTime();
	}

	public void populate()
	{
		UserEntity gestionnaire = userService.findUserByUsername("gerant");

		ArtisteEntity a1 = newArtiste("Un mec random", null);
		ArtisteEntity a2 = newArtiste("Ahlame DOUZAL", null);
		ArtisteEntity a3 = newArtiste("Patricia LADRET", null);
		ArtisteEntity a4 = newArtiste("Le mec d'IHM", null);

		artisteService.save(a1);
		artisteService.save(a2);
		artisteService.save(a3);
		artisteService.save(a4);

		SalleEntity sa1 = newSalle("Polytech", "14 Place du Conseil National de la Résistance", "Saint-Martin-d'Hères", 150, 100, 100, gestionnaire);
		SalleEntity sa2 = newSalle("IMAG", "60 Rue de la Chimie", "Saint-Martin-d'Hères", 130, 50, 40, gestionnaire);

		salleService.save(sa1);
		salleService.save(sa2);

		SpectacleEntity sp1 = newSpectacle("Traitements multimédia avancés", SpectacleGenre.CABARET, EnumSet.of(SpectaclePublicc.AMIS, SpectaclePublicc.FAMILLE), "Compression", "Mumuse avec des transformées discrètes", Collections.singletonList(a1));
		SpectacleEntity sp2 = newSpectacle("Science des données", SpectacleGenre.THEATRE, EnumSet.of(SpectaclePublicc.ENFANT, SpectaclePublicc.COUPLE, SpectaclePublicc.FAMILLE), "Classification, kmeans, hcluster, PAM", "Avec plusieurs arbres on fait une forêt", Collections.singletonList(a2));
		SpectacleEntity sp3 = newSpectacle("Veille technologique", SpectacleGenre.SPECTACLE, EnumSet.of(SpectaclePublicc.AMIS), "Présentation de technologies innovantes", "Des mecs qui nous font une présentation de leur travail pendant 2 heures", Collections.singletonList(a3));
		SpectacleEntity sp4 = newSpectacle("TP IHM avancée", SpectacleGenre.CINEMA, EnumSet.of(SpectaclePublicc.ADOLESCENT, SpectaclePublicc.AMIS), "Le fond du fun", "On fait un truc hyper dur pendant que le prof mate des trucs sur son PC", Collections.singletonList(a4));

		spectacleService.save(sp1);
		spectacleService.save(sp2);
		spectacleService.save(sp3);
		spectacleService.save(sp4);

		RepresentationEntity rp1 = newRepresentation(getDate(2017, Calendar.DECEMBER, 11), 25, 10, 10, sa1, sp3);
		RepresentationEntity rp2 = newRepresentation(getDate(2017, Calendar.DECEMBER, 13), 15, 15, 15, sa1, sp2);
		RepresentationEntity rp3 = newRepresentation(getDate(2017, Calendar.DECEMBER, 13), 16, 17, 18, sa2, sp1);
		RepresentationEntity rp4 = newRepresentation(getDate(2017, Calendar.DECEMBER, 15), 20, 30, 20, sa2, sp4);

		representationService.save(rp1);
		representationService.save(rp2);
		representationService.save(rp3);
		representationService.save(rp4);
	}
}
