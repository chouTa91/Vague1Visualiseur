package fr.polemploi.suivi.migration.api.parsers;

import java.util.List;
import java.util.Map;

import fr.polemploi.suivi.migration.entities.enums.dl1.DL1TableEnum;
import fr.polemploi.suivi.migration.entities.message.logs.DL1LogMessage;
import fr.polemploi.suivi.migration.entities.message.logs.DB2LoadMessage;

public interface DL1LogParser<Message> {

	/**
	 * Parse un fichier de log pour en alimenter une liste de POJO.
	 *
	 * @param message
	 *                  {@link String} Le message à parser.
	 * @param bySegment
	 *                  {@link boolean} true si le traitement doit être fait par
	 *                  segment, false autrement.
	 * @return {@link Map}<<{@link String}>, {@link DB2LoadMessage}> la map des
	 *         informations parsées.<br>
	 *         <b>Key</b> : le nom de la table.<br>
	 *         <b>Value</b> : les informations de la table.
	 */
	Map<String, DL1LogMessage> parse(String message, List<DL1TableEnum> tables, boolean bySegment);
}
