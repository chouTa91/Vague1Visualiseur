package fr.polemploi.suivi.migration.service;

import java.io.IOException;
import java.util.List;

import fr.polemploi.suivi.migration.entities.message.logs.RawFile;

public interface FilesRetriever {

	/**
	 * Donne les logs de DL1 sans traitement (raw).
	 *
	 * @param tableName
	 * @return
	 */
	List<RawFile> getDL1RawLogsFiles(String tableName);

	/**
	 * Donne les logs de charge de DL1 sans traitement (raw).
	 *
	 * @param tableName
	 * @return
	 * @throws IOException
	 */
	List<RawFile> getDL1RawLogsChargeFiles(String tableName) throws IOException;

	/**
	 * Donne les logs de charge de DL1 sans traitement (raw) pour une table
	 * spécifiquement..
	 *
	 * @param tableName
	 * @return
	 * @throws IOException
	 */
	List<RawFile> getDL1RawLogsFilesTable(String tableName) throws IOException;

	/**
	 * Donne les logs de DL2 sans traitement (raw).
	 *
	 * @param tableName
	 * @return
	 */
	List<RawFile> getDB2RawLogFile(String tableName);
}
