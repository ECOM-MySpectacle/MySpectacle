package org.applicationn.test;

import java.util.*;

import org.applicationn.domain.*;
import org.applicationn.domain.security.UserEntity;
import org.applicationn.search.criteria.salle.Region;
import org.applicationn.service.ArtisteService;
import org.applicationn.service.RepresentationService;
import org.applicationn.service.SalleService;
import org.applicationn.service.SpectacleService;
import org.applicationn.service.security.UserService;

public class DataSet
{
	private final ArtisteService artisteService;
	private final SpectacleService spectacleService;
	private final SalleService salleService;
	private final RepresentationService representationService;
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
		entity.setVille(region.getRegion());
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

		//Creation des artistes

		ArtisteEntity michaelJackson = newArtiste("Michael Jackson", null);
		artisteService.save(michaelJackson);

		ArtisteEntity michaelJones = newArtiste("Michael Jones", null);
		artisteService.save(michaelJones);

		ArtisteEntity ledZeppelin = newArtiste("Led Zeppelin", null);
		artisteService.save(ledZeppelin);

		ArtisteEntity patrickSebastien = newArtiste("Patrick Sebastien", null);
		artisteService.save(patrickSebastien);

		ArtisteEntity kevAdams = newArtiste("Kev Adams", null);
		artisteService.save(kevAdams);

		ArtisteEntity psg = newArtiste("PSG", null);
		artisteService.save(psg);

		ArtisteEntity asse = newArtiste("ASSE", null);
		artisteService.save(asse);

		ArtisteEntity cirquePinder = newArtiste("Cirque Pinder", null);
		artisteService.save(cirquePinder);

		ArtisteEntity irinaKolesnikova = newArtiste("Irina Kolesnikova", null);
		artisteService.save(irinaKolesnikova);

		//Creation des salles

		SalleEntity stadeDeFrance = newSalle("Stade de France", "ZAC du Cornillon, Saint-Denis",  Region.ILE_DE_FRANCE, 50000, 20000, 500, gestionnaire);
		salleService.save(stadeDeFrance);

		SalleEntity mc2 = newSalle("MC2", "4 Rue Paul Claudel, Grenoble",  Region.AUVERGNE_RHONE_ALPES, 895, 230, 50, gestionnaire);
		salleService.save(mc2);

		SalleEntity halleTonyGarnier = newSalle("Halle Tony Garnier", "20 Place docteur Charles, Lyon",  Region.AUVERGNE_RHONE_ALPES, 15000, 3000, 50, gestionnaire);
		salleService.save(halleTonyGarnier);

		SalleEntity amphiWeil =newSalle("Amphi Weil", "701 Avenue Centrale, Saint-Martin-d'Heres",  Region.AUVERGNE_RHONE_ALPES, 1000, 0, 0, gestionnaire);
		salleService.save(amphiWeil);

		//Creation des spectacles

		SpectacleEntity badWorldTour = newSpectacle("Bad World Tour", SpectacleGenre.CONCERT, EnumSet.of(SpectaclePublicc.FAMILLE), "Musique pop", "Première tournée mondiale solo de Michael Jackson, donnée suite à la sortie de l'album Bad. Un spectacle à ne manquer sous AUCUN prétexte!", Collections.singletonList(michaelJackson));
		spectacleService.save(badWorldTour);

		SpectacleEntity masterClass = newSpectacle("Au Tour De 2017/2018", SpectacleGenre.CONCERT, EnumSet.of(SpectaclePublicc.FAMILLE), "Musique rock", "Pour la sortie de son dernier album Au Tour De, l'ancien guitariste de Jean-Jacques Goldman retourne sur les routes de France afin de renouer avec son public. Venez vous laisser bercer par les mélodies blues et rock du guitariste soliste dans un concert riche en émotions.", Collections.singletonList(michaelJones));
		spectacleService.save(masterClass);

		SpectacleEntity autumnTour = newSpectacle("Autumn 1969 European Tour", SpectacleGenre.CONCERT, EnumSet.of(SpectaclePublicc.ADULTE), "Hard rock", "Le groupe Led Zeppelin revient sur les devants de la scène Européenne pour promouvoir son prochain album Led Zeppelin II. Après l'immense succes de leur album éponyme Led Zeppelin (Good Times Bad Times, Babe I'm Gonna Leave You), cette tournée s'annonce historique. A réserver rapidement!", Collections.singletonList(ledZeppelin));
		spectacleService.save(autumnTour);

		SpectacleEntity caVaBouger = newSpectacle("Ca va bouger!", SpectacleGenre.HUMOUR, EnumSet.of(SpectaclePublicc.FAMILLE), "Imitations", "Après avoir abandonné pendant quinze ans le show uniquement consacré à la parodie pour se consacrer à sa carrière de chanteur festif, Patrick retourne a ses origines avec ce spectacle poilant.", Collections.singletonList(patrickSebastien));
		spectacleService.save(caVaBouger);

		SpectacleEntity kev = newSpectacle("Kev & Gad, tout est possible !", SpectacleGenre.HUMOUR, EnumSet.of(SpectaclePublicc.FAMILLE), "Two-men show", "Kev Adams, revient dans son nouveau spectacle avec sa vingtaine, sa famille, ses cheveux, son époque, ses cheveux, ses potes, sa mère, ses cheveux, son énergie, son talent, ses cheveux.... Et même ses cheveux.", Collections.singletonList(kevAdams));
		spectacleService.save(kev);

		SpectacleEntity ligueDesChampions = newSpectacle("Matchs de Ligue des Champions du PSG", SpectacleGenre.SPORT, EnumSet.of(SpectaclePublicc.AMIS), "Football", "Suivez le parcours du Paris Saint Germain dans la plus grande compétition de football en club du monde! Retrouvez Neymar, Cavani et le jeune Mbappé dans des matchs d'anthologie.", Collections.singletonList(psg));
		spectacleService.save(ligueDesChampions);

		SpectacleEntity pls = newSpectacle("Matchs en Ligue 1 de l'AS Saint-Etienne", SpectacleGenre.SPORT, EnumSet.of(SpectaclePublicc.AMIS), "Football", "Après le départ de leur entraineur Christophe Galtier à la fin de la saison 2017, le club Stéphanois est en difficulté en championnat. Venez suivre leurs matchs pour voir le jeu flamboyant de ce grand club Français!", Collections.singletonList(asse));
		spectacleService.save(pls);

		SpectacleEntity cirque = newSpectacle("tournée 2017/2018", SpectacleGenre.CIRQUE, EnumSet.of(SpectaclePublicc.FAMILLE), "Cirque", "Venez en famille découvrir le nouveau spectacle du cirque Pinder! Au programme, clowns, tigres, éléphants et acrobrates pour la joie des petits commes des grands!", Collections.singletonList(cirquePinder));
		spectacleService.save(cirque);
		
		SpectacleEntity leLacDesCygnes = newSpectacle("Le Lac Des Cygnes", SpectacleGenre.SPECTACLE, EnumSet.of(SpectaclePublicc.ADULTE), "Ballet", "Le célèbre et intemporel Lac des Cygnes, dansé par Irina Kolesnikova sur la musique de Piotr Ilitch Tchaïkovski, vous transportera dans un monde de grâce, de puissance et d'élégance", Collections.singletonList(irinaKolesnikova));
		spectacleService.save(leLacDesCygnes);

		//Création des représentations

		RepresentationEntity rp1 = newRepresentation(getDate(2017, Calendar.DECEMBER, 4), 0, 0, 0, amphiWeil, caVaBouger);
		RepresentationEntity rp2 = newRepresentation(getDate(2017, Calendar.DECEMBER, 4), 0, 50, 45, mc2, kev);
		RepresentationEntity rp3 = newRepresentation(getDate(2017, Calendar.DECEMBER, 4), 5000, 1000, 50, halleTonyGarnier, leLacDesCygnes);
		RepresentationEntity rp4 = newRepresentation(getDate(2017, Calendar.DECEMBER, 5), 20000, 10000, 0, stadeDeFrance, badWorldTour);
		RepresentationEntity rp5 = newRepresentation(getDate(2017, Calendar.DECEMBER, 5), 500, 0, 0, amphiWeil, masterClass);
		RepresentationEntity rp6 = newRepresentation(getDate(2017, Calendar.DECEMBER, 5), 700, 20, 5, mc2, cirque);
		RepresentationEntity rp7 = newRepresentation(getDate(2017, Calendar.DECEMBER, 6), 200, 100, 12, halleTonyGarnier, autumnTour);
		RepresentationEntity rp8 = newRepresentation(getDate(2017, Calendar.DECEMBER, 6), 30000, 15000, 300, stadeDeFrance, ligueDesChampions);
		RepresentationEntity rp9 = newRepresentation(getDate(2017, Calendar.DECEMBER, 6), 5, 3, 7, amphiWeil, caVaBouger);
		RepresentationEntity rp10 = newRepresentation(getDate(2017, Calendar.DECEMBER, 6), 600, 0, 0, mc2, masterClass);
		RepresentationEntity rp11 = newRepresentation(getDate(2017, Calendar.DECEMBER, 7), 14000, 205, 7, halleTonyGarnier, leLacDesCygnes);
		RepresentationEntity rp12 = newRepresentation(getDate(2017, Calendar.DECEMBER, 8), 50000, 20000, 500, stadeDeFrance, pls);
		RepresentationEntity rp13 = newRepresentation(getDate(2017, Calendar.DECEMBER, 8), 10000, 14000, 14, stadeDeFrance, autumnTour);
		RepresentationEntity rp14 = newRepresentation(getDate(2017, Calendar.DECEMBER, 8), 200, 100, 10, halleTonyGarnier, badWorldTour);
		RepresentationEntity rp15 = newRepresentation(getDate(2017, Calendar.DECEMBER, 8), 1000, 500, 1, stadeDeFrance, ligueDesChampions);

		representationService.save(rp1);
		representationService.save(rp2);
		representationService.save(rp3);
		representationService.save(rp4);
		representationService.save(rp5);
		representationService.save(rp6);
		representationService.save(rp7);
		representationService.save(rp8);
		representationService.save(rp9);
		representationService.save(rp10);
		representationService.save(rp11);
		representationService.save(rp12);
		representationService.save(rp13);
		representationService.save(rp14);
		representationService.save(rp15);
	}
}
