package fr.polemploi.suivi.migration.service.validator;

import fr.polemploi.suivi.migration.entities.alert.Alert;

/**
 * Interface de remonté des alertes fonctionnelles.
 *
 * @author jbourrea
 *
 */
public interface AlertValidator {

	/**
	 * Verification des fichiers manquants pour DL1, vague 1.
	 *
	 * @param alert
	 */
	void dl1CheckMissingFileVague1(Alert alert);

	/**
	 * Verification de la volumétrie, vague 1.
	 *
	 * @param alert
	 */
	void dl1CheckVolumetrieVague1(Alert alert);

}
