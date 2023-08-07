package fr.polemploi.suivi.migration.service.validator;

import java.util.List;

import fr.polemploi.suivi.migration.entities.exception.TirErrors;
import fr.polemploi.suivi.migration.entities.tir.TirDetailDB2;
import fr.polemploi.suivi.migration.entities.tir.TirDetailDL1;

/**
 * Validation des tirs, alimentation des erreurs.
 *
 * @author jbourrea
 *
 */
public interface Validator {

	/**
	 * Donne la liste des erreurs pour un tir donné pour DL1.
	 *
	 * @param tir
	 *            {@link TirDetailDL1} Le tir dont on veut extraire les erreurs.
	 * @return {@link List}<{@link TirErrors}> La liste des erreurs.
	 */
	public List<TirErrors> getErrorsDL1(TirDetailDL1 tir);

	/**
	 * Donne la liste des erreurs pour un tir donné pour DB2.
	 *
	 * @param tir
	 *            {@link TirDetailDL1} Le tir dont on veut extraire les erreurs.
	 * @return {@link List}<{@link TirErrors}> La liste des erreurs.
	 */
	List<TirErrors> getErrorsDB2(TirDetailDB2 tir);

}
