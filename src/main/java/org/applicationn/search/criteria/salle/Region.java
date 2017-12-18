package org.applicationn.search.criteria.salle;

import java.util.Arrays;

public enum Region
{
	AUVERGNE_RHONE_ALPES("Auvergne-Rhône-Alpes"),
	BOURGOGNE_FRANCHE_COMTE("Bourgogne-Franche-Comté"),
	BRETAGNE("Bretagne"),
	CENTRE_VAL_DE_LOIRE("Centre-Val de Loire"),
	CORSE("Corse"),
	GRAND_EST("Grand Est"),
	HAUTS_DE_FRANCE("Hauts-de-France"),
	ILE_DE_FRANCE("Île-de-France"),
	NORMANDIE("Normandie"),
	NOUVELLE_AQUITAINE("Nouvelle-Aquitaine"),
	OCCITANIE("Occitanie"),
	PAYS_DE_LA_LOIRE("Pays de la Loire"),
	PROVENCE_ALPES_COTE_D_AZUR("Provence-Alpes-Côte d'Azur");

	private final String region;

	Region(String region)
	{
		this.region = region;
	}

	public String getRegion()
	{
		return region;
	}

	public static Region get(String value)
	{
		return Arrays.stream(values()).filter(region -> region.region.equalsIgnoreCase(value)).findFirst().orElse(null);
	}
}
