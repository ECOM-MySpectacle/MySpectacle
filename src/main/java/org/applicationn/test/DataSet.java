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

		ArtisteEntity lydieDuBousquet = newArtiste("Lydie DU BOUSQUET", null);
		ArtisteEntity yvesLedru = newArtiste("Yves LEDRU", null);
		ArtisteEntity bernardTourancheau = newArtiste("Bernard TOURANCHEAU", null);
		ArtisteEntity sybilleCaffiau = newArtiste("Sybille CAFFIAU", null);
		ArtisteEntity didierDonsez = newArtiste("Didier DONSEZ", null);
		ArtisteEntity massihRezaAmini = newArtiste("Massih-Reza AMINI", null);
		ArtisteEntity laurenceNigay = newArtiste("Laurence NIGAY", null);
		ArtisteEntity ahlameDouzal = newArtiste("Ahlame DOUZAL", null);
		ArtisteEntity patriciaLadret = newArtiste("Patricia LADRET", null);
		ArtisteEntity timothyClaeys = newArtiste("Timothy CLAEYS", null);
		ArtisteEntity englishTeacher = newArtiste("English Teacher", null);
		ArtisteEntity jeanLucRichier = newArtiste("Jean-Luc RICHIER", null);
		ArtisteEntity aliJabari = newArtiste("Ali JABARI", null);
		ArtisteEntity jeremyWambecke = newArtiste("Jérémy WAMBECKE", null);
		ArtisteEntity dominiqueBarthel = newArtiste("Dominique BARTHEL", null);

		artisteService.save(lydieDuBousquet);
		artisteService.save(yvesLedru);
		artisteService.save(bernardTourancheau);
		artisteService.save(sybilleCaffiau);
		artisteService.save(didierDonsez);
		artisteService.save(massihRezaAmini);
		artisteService.save(laurenceNigay);
		artisteService.save(ahlameDouzal);
		artisteService.save(patriciaLadret);
		artisteService.save(timothyClaeys);
		artisteService.save(englishTeacher);
		artisteService.save(jeanLucRichier);
		artisteService.save(aliJabari);
		artisteService.save(jeremyWambecke);
		artisteService.save(dominiqueBarthel);

		SalleEntity pg257 = newSalle("PG salle 257", "Polytech", "Saint-Martin-d'Hères", 5, 8, 39, gestionnaire); // 52
		SalleEntity pg144 = newSalle("PG salle 144", "Polytech", "Saint-Martin-d'Hères", 7, 10, 49, gestionnaire); // 66
		SalleEntity pg007 = newSalle("PG amphi 007", "Polytech", "Saint-Martin-d'Hères", 6, 9, 46, gestionnaire); // 61
		SalleEntity imagF216 = newSalle("IMAG TP F216", "IMAG", "Saint-Martin-d'Hères", 5, 10, 20, gestionnaire); // ?
		SalleEntity imagF217 = newSalle("IMAG TP F217", "IMAG", "Saint-Martin-d'Hères", 6, 6, 18, gestionnaire); // ?
		SalleEntity imagF202 = newSalle("IMAG TP F202", "IMAG", "Saint-Martin-d'Hères", 12, 10, 30, gestionnaire); // ?
		SalleEntity imagF204 = newSalle("IMAG TP F204", "IMAG", "Saint-Martin-d'Hères", 8, 11, 25, gestionnaire); // ?
		SalleEntity imagF117 = newSalle("IMAG TD F117", "IMAG", "Saint-Martin-d'Hères", 13, 17, 50, gestionnaire); // ?
		SalleEntity pg011 = newSalle("PG salle 011", "Polytech", "Saint-Martin-d'Hères", 10, 5, 30, gestionnaire); // 45
		SalleEntity imagF101 = newSalle("IMAG TP F101", "IMAG", "Saint-Martin-d'Hères", 11, 18, 27, gestionnaire); // ?
		SalleEntity imagF102 = newSalle("IMAG TP F102", "IMAG", "Saint-Martin-d'Hères", 14, 10, 29, gestionnaire); // ?
		SalleEntity imagF319 = newSalle("IMAG TD F319", "IMAG", "Saint-Martin-d'Hères", 6, 6, 20, gestionnaire); // ?
		SalleEntity pgAnglais = newSalle("PG salle anglais", "Polytech", "Saint-Martin-d'Hères", 4, 5, 27, gestionnaire); // 36
		SalleEntity imagF321 = newSalle("IMAG TD F321", "IMAG", "Saint-Martin-d'Hères", 9, 9, 23, gestionnaire); // ?
		SalleEntity pg035 = newSalle("PG salle 035", "Polytech", "Saint-Martin-d'Hères", 3, 5, 23, gestionnaire); // 32
		SalleEntity pg052 = newSalle("PG salle 052", "Polytech", "Saint-Martin-d'Hères", 8, 12, 30, gestionnaire); // 60

		salleService.save(pg257);
		salleService.save(pg144);
		salleService.save(pg007);
		salleService.save(imagF216);
		salleService.save(imagF217);
		salleService.save(imagF117);
		salleService.save(pg011);
		salleService.save(imagF101);
		salleService.save(imagF102);
		salleService.save(imagF319);
		salleService.save(pgAnglais);
		salleService.save(imagF321);
		salleService.save(imagF204);
		salleService.save(pg035);
		salleService.save(pg052);

		SpectacleEntity gl = newSpectacle("Génie logiciel", SpectacleGenre.CABARET, EnumSet.of(SpectaclePublicc.AMIS, SpectaclePublicc.FAMILLE), "Architecture et tests logiciels", "Techniques de conception d'architecture et types de tests", Arrays.asList(lydieDuBousquet, yvesLedru));
		SpectacleEntity glTD = newSpectacle("TD Génie logiciel", SpectacleGenre.THEATRE, EnumSet.of(SpectaclePublicc.FAMILLE), "TD Architecture et tests logiciels", "La version TD", Arrays.asList(lydieDuBousquet, yvesLedru));
		SpectacleEntity vt = newSpectacle("Veuille technologique", SpectacleGenre.SPECTACLE, EnumSet.of(SpectaclePublicc.AMIS, SpectaclePublicc.FAMILLE, SpectaclePublicc.ENFANT), "Présentation d'entreprise", "Parcours, entreprise, technologies", Collections.singletonList(bernardTourancheau));
		SpectacleEntity ecomMatin = newSpectacle("Projet ECOM (matin)", SpectacleGenre.LOISIR, EnumSet.of(SpectaclePublicc.AMIS), "Conception d'un logiciel e-commerce", "Voir la page wiki AIR", Arrays.asList(didierDonsez, sybilleCaffiau));
		SpectacleEntity ecomApresMidi = newSpectacle("Projet ECOM (après-midi)", SpectacleGenre.LOISIR, EnumSet.of(SpectaclePublicc.AMIS), "Conception d'un logiciel e-commerce", "Voir la page wiki AIR", Arrays.asList(didierDonsez, sybilleCaffiau));
		SpectacleEntity sd = newSpectacle("Science des données", SpectacleGenre.THEATRE, EnumSet.of(SpectaclePublicc.ADULTE, SpectaclePublicc.COUPLE), "Classification, kmeans, hcluster, PAM", "Avec plusieurs arbres on fait une forêt", Arrays.asList(ahlameDouzal, massihRezaAmini));
		SpectacleEntity ihma = newSpectacle("Interaction homme-machine avancée", SpectacleGenre.SPECTACLEENFANT, EnumSet.of(SpectaclePublicc.ENFANT), "Techniques de menus", "Y en a plein mais ils servent pas...", Collections.singletonList(laurenceNigay));
		SpectacleEntity sdTP = newSpectacle("TP Science des données", SpectacleGenre.FESTIVAL, EnumSet.of(SpectaclePublicc.FAMILLE), "TP Classification, kmeans, hcluster, PAM", "La version TP", Arrays.asList(ahlameDouzal, massihRezaAmini));
		SpectacleEntity secuTP = newSpectacle("TP Sécurité", SpectacleGenre.SPECTACLE, EnumSet.of(SpectaclePublicc.AMIS), "La sécurité de suppose", "Je ne suis pas en réseau", Collections.singletonList(timothyClaeys));
		SpectacleEntity tma = newSpectacle("Traitements multimédia avancés", SpectacleGenre.THEATRE, EnumSet.of(SpectaclePublicc.AMIS), "Méthodes de compression", "JPEG, MPEG, etc.", Collections.singletonList(patriciaLadret));
		SpectacleEntity anglais = newSpectacle("Anglais", SpectacleGenre.FESTIVAL, EnumSet.of(SpectaclePublicc.ADOLESCENT, SpectaclePublicc.ADULTE), "Cours d'anglais", "Les speeches et débâts en anglais c'est cool", Collections.singletonList(englishTeacher));
		SpectacleEntity ari = newSpectacle("Administration réseaux infrastructure", SpectacleGenre.CINEMA, EnumSet.of(SpectaclePublicc.ADULTE), "Des trucs sur l'administration des réseaux ?", "Je ne suis pas en réseau", Collections.singletonList(jeanLucRichier));
		SpectacleEntity ihmaTP = newSpectacle("TP Interaction homme-machine avancée", SpectacleGenre.HUMOUR, EnumSet.of(SpectaclePublicc.ENFANT, SpectaclePublicc.FAMILLE), "Programmation d'un bubbling menu", "C'est hyper long et dur", Collections.singletonList(aliJabari));
		SpectacleEntity wsn = newSpectacle("WSN", SpectacleGenre.CONCERT, EnumSet.of(SpectaclePublicc.FAMILLE), "Aucune idée", "Je ne suis pas en réseau", Collections.singletonList(dominiqueBarthel));
		SpectacleEntity vdTP = newSpectacle("TP Visualisation de données", SpectacleGenre.CABARET, EnumSet.of(SpectaclePublicc.COUPLE, SpectaclePublicc.AMIS), "Visualisation de données météo dans Google Earth", "C'est stylé, mais configurer Linux avec ParaView et tout...", Collections.singletonList(jeremyWambecke));

		spectacleService.save(gl);
		spectacleService.save(glTD);
		spectacleService.save(vt);
		spectacleService.save(ecomMatin);
		spectacleService.save(ecomApresMidi);
		spectacleService.save(sd);
		spectacleService.save(ihma);
		spectacleService.save(sdTP);
		spectacleService.save(secuTP);
		spectacleService.save(tma);
		spectacleService.save(anglais);
		spectacleService.save(ari);
		spectacleService.save(ihmaTP);
		spectacleService.save(wsn);
		spectacleService.save(vdTP);

		RepresentationEntity rp1 = newRepresentation(getDate(2017, Calendar.DECEMBER, 4), 0, 3, 10, pg257, gl);
		RepresentationEntity rp2 = newRepresentation(getDate(2017, Calendar.DECEMBER, 4), 0, 0, 0, pg144, glTD);
		RepresentationEntity rp3 = newRepresentation(getDate(2017, Calendar.DECEMBER, 4), 1, 0, 0, pg007, vt);
		RepresentationEntity rp4 = newRepresentation(getDate(2017, Calendar.DECEMBER, 5), 2, 1, 4, imagF216, ecomMatin);
		RepresentationEntity rp5 = newRepresentation(getDate(2017, Calendar.DECEMBER, 5), 2, 1, 0, imagF117, sd);
		RepresentationEntity rp6 = newRepresentation(getDate(2017, Calendar.DECEMBER, 5), 0, 0, 0, imagF204, ecomApresMidi);
		RepresentationEntity rp7 = newRepresentation(getDate(2017, Calendar.DECEMBER, 6), 7, 4, 12, pg011, ihma);
		RepresentationEntity rp8 = newRepresentation(getDate(2017, Calendar.DECEMBER, 6), 2, 2, 2, pg011, sdTP);
		RepresentationEntity rp9 = newRepresentation(getDate(2017, Calendar.DECEMBER, 6), 5, 3, 7, imagF101, secuTP);
		RepresentationEntity rp10 = newRepresentation(getDate(2017, Calendar.DECEMBER, 6), 1, 0, 5, imagF319, tma);
		RepresentationEntity rp11 = newRepresentation(getDate(2017, Calendar.DECEMBER, 7), 0, 0, 7, pgAnglais, anglais);
		RepresentationEntity rp12 = newRepresentation(getDate(2017, Calendar.DECEMBER, 8), 1, 2, 2, imagF321, ari);
		RepresentationEntity rp13 = newRepresentation(getDate(2017, Calendar.DECEMBER, 8), 4, 5, 14, imagF204, ihmaTP);
		RepresentationEntity rp14 = newRepresentation(getDate(2017, Calendar.DECEMBER, 8), 2, 6, 10, pg052, wsn);
		RepresentationEntity rp15 = newRepresentation(getDate(2017, Calendar.DECEMBER, 8), 0, 0, 1, pg035, vdTP);

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
