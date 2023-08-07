package fr.polemploi.suivi.migration.service;

import fr.polemploi.suivi.migration.entities.enums.common.DirectionRegionaleEnum;
import fr.polemploi.suivi.migration.entities.tir.DRSynthesis;

/**
 * Interface de collection des informations relative a une DR.
 *
 * @author jbourrea
 *
 */
public interface DRCompiler {

	/**
	 * Récupère la synthèse d'une DR.
	 *
	 * @param dr
	 *           {@link DirectionRegionaleEnum} la direction dont on veut les
	 *           informations.
	 * @return
	 */
	public DRSynthesis gatherDRSynthesis(DirectionRegionaleEnum dr);
}
