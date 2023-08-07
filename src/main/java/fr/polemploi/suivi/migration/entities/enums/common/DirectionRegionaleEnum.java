package fr.polemploi.suivi.migration.entities.enums.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumération des directions régionales;
 *
 * @author jbourrea
 *
 *         TODO JBOU compléter les infos.
 *
 */
public enum DirectionRegionaleEnum {

	DR00("DR00", "", "", ""),
	DR01("DR01", "AQUITAINE", "Bordeaux", "33000"),
	DR12("DR12", "LIMOUSIN", "Limoges", "87000"),
	DR13("DR13", "PAYS DE LA LOIRE", "Nantes", "44000"),
	DR16("DR16", "SUD EST FRANCILIEN", "", ""),
	DR17("DR17", "ALSACE", "Strasbourg", "67073"),
	DR20("DR20", "FRANCHE COMTE", "Besançon", "25000"),
	DR24("DR24", "ALPES", "Lyon", "69000"),
	DR25("DR25", "PICARDIE", "Amiens", "80000"),
	DR26("DR26", "PAS DE CALAIS", "Arras", "62000"),
	DR27("DR27", "BRETAGNE", "Rennes", "35000"),
	DR31("DR31", "VALLEES RHONE LOIRE", "", ""),
	DR32("DR32", "COTE D AZUR", "Nice", "06000"),
	DR34("DR34", "ALPES PROVENCE", "", ""),
	DR35("DR35", "CENTRE", "Châteauroux", ""),
	DR39("DR39", "POITOU CHARENTES", "Poitiers", "86000"),
	DR40("DR40", "BASSE NORMANDIE", "Caen", "14000"),
	DR41("DR41", "HAUTE NORMANDIE", "Rouen", "76000"),
	DR44("DR44", "AUVERGNE", "Clermont-Ferrand", "63000"),
	DR46("DR46", "LANGUEDOC ROUSSILLON", "Montpellier", "34000"),
	DR48("DR48", "MIDI PYRENEES", "Toulouse", "31000"),
	DR49("DR49", "PAYS DU NORD", "Lille", "59000"),
	DR50("DR50", "BOURGOGNE", "Dijon", "21000"),
	DR51("DR51", "CHAMPAGNE ARDENNES", "Châlons-en-Champagne", "51108"),
	DR56("DR56", "PARIS", "", ""),
	DR57("DR57", "OUEST FRANCILIEN", "", ""),
	DR60("DR60", "OUEST FRANCILIEN (CATS)", "", ""),
	DR61("DR61", "EST FRANCILIEN", "", ""),
	DR63("DR63", "LORRAINE", "", ""),
	DR65("DR65", "CORSE", "", ""),
	DR66("DR66", "GUADELOUPE", "", ""),
	DR67("DR67", "MARTINIQUE", "", ""),
	DR68("DR68", "LA REUNION", "", ""),
	DR69("DR69", "GUYANE", "", ""),
	DR70("DR70", "SAINT PIERRE ET MIQUELON", "", ""),
	DR71("DR71", "MAYOTTE", "Mamoudzou", "97600");

	private String code;
	private String libelle;
	private String prefecture;
	private String codePostal;

	private DirectionRegionaleEnum(String code, String libelle, String prefecture, String codePostal) {
		this.setCode(code);
		this.setLibelle(libelle);
		this.setPrefecture(prefecture);
		this.setCodePostal(codePostal);
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getPrefecture() {
		return this.prefecture;
	}

	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
	}

	public String getCodePostal() {
		return this.codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	/**
	 * Donne la liste des DR à prendre en compte sur le serveur actuel.
	 *
	 * @return
	 */
	public static List<DirectionRegionaleEnum> getDRByCible(String cible) {
		List<DirectionRegionaleEnum> drList = new ArrayList<>();

		switch (cible) {
		case "CadreQ": { // TODO JBOU Enum pour les cadres cibles ?
			drList.add(DR00);
			drList.add(DR32);
			drList.add(DR65);
			break;
		}
		case "CadreR": {
			for (DirectionRegionaleEnum dr : DirectionRegionaleEnum.values()) {
				drList.add(dr);
			}
			break;
		}
		case "Prod": {
			for (DirectionRegionaleEnum dr : DirectionRegionaleEnum.values()) {
				drList.add(dr);
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Serveur (cadre) cible inconu :" + cible);
		}

		return drList;
	}

}
