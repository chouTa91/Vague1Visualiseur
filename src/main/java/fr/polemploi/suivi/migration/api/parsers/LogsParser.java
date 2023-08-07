package fr.polemploi.suivi.migration.api.parsers;

import java.util.List;

import fr.polemploi.suivi.migration.entities.message.logs.DB2LoadMessage;

/**
 * Interface de parsing des fichiers de logs.
 *
 * @author jbourrea
 *
 */
public interface LogsParser<Message> {

	/**
	 * Parse un fichier de log pour en alimenter une liste de POJO.
	 *
	 * @param message
	 *                {@link String} Le message à parser.
	 * @return {@link List}<{@link DB2LoadMessage}> la liste des messages parsés.
	 */
	List<Message> parseAll(String message);

}
