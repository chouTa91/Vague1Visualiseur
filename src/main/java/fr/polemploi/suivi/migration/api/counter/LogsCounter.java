package fr.polemploi.suivi.migration.api.counter;

import fr.polemploi.suivi.migration.entities.db2.DB2TablesFiles;
import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesFiles;
import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesFilesContainer;
import fr.polemploi.suivi.migration.entities.enums.common.DirectionRegionaleEnum;

/**
 * Compteur de fichier pour le contrôle des livrables.
 *
 * @author jbourrea
 *
 */
public interface LogsCounter {

	/**
	 * Vérification du nombre de fichiers livrés pour la DL1 pour chaque DR.
	 *
	 * @return {@link DL1DirectionRegionaleTablesFilesContainer} les informations
	 *         concernant les fichiers DL1 par DR.
	 */
	DL1DirectionRegionaleTablesFilesContainer getDL1MissingFilesFromDirectory();

	/**
	 * Vérification du nombre de fichiers livrés pour la DB2.
	 *
	 * @return {@link DL1DirectionRegionaleTablesFiles} les informations concernant
	 *         les fichiers DB2.
	 */
	DB2TablesFiles getDB2MissingFilesFromDirectory();

	/**
	 * Vérification du nombre de fichiers logs générés pour la DL1 pour chaque DR.
	 *
	 * @return {@link DL1DirectionRegionaleTablesFilesContainer} les informations
	 *         concernant les logs DL1 par DR.
	 */
	DL1DirectionRegionaleTablesFilesContainer getDL1MissingLogsFromDirectory();

	/**
	 * Vérification du nombre de fichiers livrés pour la DL1 pour une direction
	 * donnée.
	 *
	 * @return {@link DL1DirectionRegionaleTablesFilesContainer} les informations
	 *         concernant les fichiers DL1 pour la DR.
	 */
	DL1DirectionRegionaleTablesFiles getDL1MissingFilesForDirection(DirectionRegionaleEnum directionRegionale);

}
