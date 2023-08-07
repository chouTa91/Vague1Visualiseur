package fr.polemploi.suivi.migration.service.reader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesDetailsContainer;
import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesVolumeContainer;
import fr.polemploi.suivi.migration.entities.message.logs.DB2ConvertMessage;
import fr.polemploi.suivi.migration.entities.message.logs.DB2LoadMessage;
import fr.polemploi.suivi.migration.entities.message.logs.DL1LogMessage;

/**
 * Service de lecture de logs.
 *
 * @author jbourrea
 *
 */
public interface LogsReader {

	/**
	 * Récupération et parsing des fichiers de logs.
	 *
	 * @param path
	 *             {@link String} Le chemin vers les fichiers de logs à parser.
	 * @return {@link List}<{@link DB2LoadMessage}> la liste des messages parsés.
	 * @throws IOException
	 */
	List<DB2LoadMessage> retrieveDB2LoadInfos(String path) throws IOException;

	/**
	 * Récupération et parsing des fichiers de logs pour DL1
	 *
	 * @param folderPath
	 *                   {@link String} Le chemin vers les fichiers de logs à
	 *                   parser.
	 * @return {@link List}<{@link DL1LogMessage}> la liste des messages parsés.
	 * @throws IOException
	 */
	DL1DirectionRegionaleTablesDetailsContainer retrieveDL1LogFiles(String folderPath) throws IOException;

	/**
	 * TODO JBOU récupération de la volumétrie enregistré dans Oracle pour les
	 * tables de DL1.
	 *
	 * @param dl1VolumetrieOracle
	 *                            {@link String} Le chemin vers le fichier de
	 *                            volumétrie oracle.
	 * @return
	 */
	DL1DirectionRegionaleTablesVolumeContainer retrieveDL1VolumeFile(String volumetrieOraclePath);

	/**
	 * Récupère les infotrmation de volumetrie par segment pour toutes les DR.
	 *
	 * @param folderPath
	 * @return
	 * @throws IOException
	 */
	Map<String, DL1LogMessage> retrieveDL1LogFilesBySegment(String folderPath) throws IOException;

	/**
	 * Récupère les infotrmation de conversion de DB2.
	 *
	 * @param folderPath
	 * @return
	 * @throws IOException
	 */
	List<DB2ConvertMessage> retrieveDB2ConvertInfos(String path) throws IOException;

}
