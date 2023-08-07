package fr.polemploi.suivi.migration.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.polemploi.suivi.migration.test.FichierDL1;

@Controller
public class TestLogTrackerController {

	String filePath = "C:\\Users\\jbourea\\git\\vague1visualiseur\\Vague1Visualiseur\\fichierbouchon\\listeFichierDL1fromNas.log";

	@GetMapping("/test2")
	public ModelAndView test2() throws IOException {

		ModelAndView mv = new ModelAndView("/pages/test2");

		int dl1_nbrFichiersTotal = TestLogTrackerController.nombreDeFichierTotalDansLeNAS(this.filePath);
		System.out.println("Nombre de fichier DL1 : " + dl1_nbrFichiersTotal);

		Set<String> dl1_listDr = TestLogTrackerController
				.rechercherDifferenteDirectionDansLeNomDesFichiers(this.filePath);

		Map<String, FichierDL1> infoFichierDL1 = new HashMap<>();

		for (String dr : dl1_listDr) {
			int nbrFichier = TestLogTrackerController.countOccurrences(this.filePath, dr);
			FichierDL1 fichierDL1 = new FichierDL1();
			fichierDL1.setNombreDeFichier(nbrFichier);
			if (dr.equalsIgnoreCase("DR65")) {
				fichierDL1.getListFichierManquant().add("fichier manquant 1");
				fichierDL1.getListFichierManquant().add("fichier manquant 2");
				fichierDL1.getListFichierManquant().add("fichier manquant 3");
			}
			infoFichierDL1.put(dr, fichierDL1);
		}

		mv.addObject("dl1_nbrFichiersTotal", dl1_nbrFichiersTotal);
		mv.addObject("dl1_listDr", dl1_listDr);
		mv.addObject("infoFichierDL1", infoFichierDL1);

		return mv;

	}

	public static int nombreDeFichierTotalDansLeNAS(String filePath) {
		int nbrFichier = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			while (reader.readLine() != null) {
				nbrFichier++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nbrFichier;
	}

	public static int countOccurrences(String filePath, String searchPattern) {
		int occurrenceCount = 0;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				occurrenceCount += TestLogTrackerController.countOccurrencesInLine(line, searchPattern);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return occurrenceCount;
	}

	public static int countOccurrencesInLine(String line, String searchPattern) {
		int count = 0;
		int lastIndex = 0;
		while (lastIndex != -1) {
			lastIndex = line.indexOf(searchPattern, lastIndex);
			if (lastIndex != -1) {
				count++;
				lastIndex += searchPattern.length();
			}
		}
		return count;
	}

	public static Set<String> rechercherDifferenteDirectionDansLeNomDesFichiers(String filePath) {

		Set<String> directions = new HashSet<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String direction = TestLogTrackerController.extractDirection(line);
				if (direction != null) {
					directions.add(direction);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return directions;
	}

	public static String extractDirection(String line) {
		String pattern = "\\.(DR\\d{2})\\.";
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(line);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}
}
