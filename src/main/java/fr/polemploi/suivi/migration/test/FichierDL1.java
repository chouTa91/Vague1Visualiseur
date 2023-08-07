package fr.polemploi.suivi.migration.test;

import java.util.ArrayList;
import java.util.List;

public class FichierDL1 {
	
	int nombreDeFichier;
	List<String> listFichierManquant = new ArrayList<>();
	
	public FichierDL1() {
		super();
	}
	public int getNombreDeFichier() {
		return nombreDeFichier;
	}
	public void setNombreDeFichier(int nombreDeFichier) {
		this.nombreDeFichier = nombreDeFichier;
	}
	public List<String> getListFichierManquant() {
		return listFichierManquant;
	}
	public void setListFichierManquant(List<String> listFichierManquant) {
		this.listFichierManquant = listFichierManquant;
	}

}
