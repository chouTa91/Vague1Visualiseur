package fr.polemploi.suivi.migration.service.validator.predicate;

import java.util.function.Predicate;

import fr.polemploi.suivi.migration.entities.alert.Alert;
import fr.polemploi.suivi.migration.entities.alert.Tuple;

/**
 * Interface de distribution des predicats fonctionnels.
 *
 * @author jbourrea
 *
 */
public interface PredicateSuiviMigration {

	/**
	 * Alerte pour les fichiers manquant des DR chef de file.
	 *
	 * @param alert
	 * @return
	 */
	Tuple<String, Predicate<Alert>> dl1CheckMissingFileVague1(Alert alert);

	/**
	 * Alerte pour la volumétrie des tables non prise en compte.
	 *
	 * @param alert
	 * @return
	 */
	Tuple<String, Predicate<Alert>> dl1CheckVolumetrieVague1(Alert alert);

	/**
	 * Alerte si l'écart entre les volumes est trop grand.
	 *
	 *
	 * @param alert
	 * @return
	 */
	Tuple<String, Predicate<Alert>> dl1CheckVolumetrieEcart(Alert alert);

}
