package fr.polemploi.suivi.migration.api.parsers.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import fr.polemploi.suivi.migration.api.parsers.LogsParser;
import fr.polemploi.suivi.migration.entities.enums.common.StatusEnum;
import fr.polemploi.suivi.migration.entities.enums.db2.DB2TableEnum;
import fr.polemploi.suivi.migration.entities.message.logs.DB2LoadMessage;

@Component
public class DB2LoadLogsParserImpl implements LogsParser<DB2LoadMessage> {

	private static final Logger logger = LoggerFactory.getLogger(DB2LoadLogsParserImpl.class);

	private static final String REGEX_NB_POST_TRAITEMENT_GROUP = "nbPostTraitement";
	private static final String REGEX_NB_PRE_TRAITEMENT_GROUP = "nbPreTraitement";
	private static final String REGEX_STATUS_GROUP = "status";
	private static final String REGEX_TABLE_GROUP = "table";
	private static final String ELAPSED_TIME = "elapsedTime";

	@Override
	public List<DB2LoadMessage> parseAll(String path) {

		List<DB2LoadMessage> messages = new ArrayList<>();

		for (DB2TableEnum table : DB2TableEnum.getDB2Tables()) {

			// Format du nom de fichier : P0101B_DR00.log
			FileSystemResource file = new FileSystemResource(path + table.getTableName() + "_DR00.log");

			if (file.getFile().exists()) {

				try {

					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(file.getInputStream(), "UTF-8"));

					DB2LoadMessage logMessage = this.readDetails(bufferedReader);

					messages.add(logMessage);

					// On ferme le robinet des streams.
					bufferedReader.close();

				} catch (IOException e) {
					DB2LoadLogsParserImpl.logger.error("Log parsing error : ", e);
				}
			}
		}

		return messages;
	}

	private DB2LoadMessage readDetails(BufferedReader bufferedReader) throws IOException {

		DB2LoadMessage logMessage = new DB2LoadMessage();

		// On cherche via regex la ligne qui nous intéresse, exemple :
		// Elapsed Time: 770 seconds
		Pattern patternElapsedTime;
		Matcher matcherElapsedTime;
		patternElapsedTime = Pattern.compile("Elapsed Time:.(?<elapsedTime>\\d*)");

		// On cherche via regex la ligne qui nous intéresse, exemple :
		// DARPPP1.P7101A;OK;11489900;11489900
		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile("(?<" + DB2LoadLogsParserImpl.REGEX_TABLE_GROUP + ">\\w*\\.\\w*);(?<"
				+ DB2LoadLogsParserImpl.REGEX_STATUS_GROUP + ">\\w*);(?<"
				+ DB2LoadLogsParserImpl.REGEX_NB_PRE_TRAITEMENT_GROUP + ">\\d*);?(?<"
				+ DB2LoadLogsParserImpl.REGEX_NB_POST_TRAITEMENT_GROUP + ">\\d*)");
		// Regex au format text (raw) :
		// (?<table>\w*\.\w*);(?<status>\w*);(?<nbPreTraitement>\d*);?(?<nbPostTraitement>\d*)

		boolean timeFound = false;

		for (String line; (line = bufferedReader.readLine()) != null;) {

			matcherElapsedTime = patternElapsedTime.matcher(line);
			if (!timeFound && matcherElapsedTime.find()) {

				// Traitement sur la ligne des données qui nous intéresse.
				// Récupération des valeurs dans les "groups" définis par la regex.
				logMessage.setElapsedTime(matcherElapsedTime.group(DB2LoadLogsParserImpl.ELAPSED_TIME));

				timeFound = true;

			}

			matcher = pattern.matcher(line);
			if (matcher.find()) {

				// Traitement sur la ligne des données qui nous intéresse.
				// Récupération des valeurs dans les "groups" définis par la regex.
				logMessage.setTable(matcher.group(DB2LoadLogsParserImpl.REGEX_TABLE_GROUP));
				logMessage.setStatus(StatusEnum.valueOf(matcher.group(DB2LoadLogsParserImpl.REGEX_STATUS_GROUP)));
				logMessage.setNbPreTraitement(matcher.group(DB2LoadLogsParserImpl.REGEX_NB_PRE_TRAITEMENT_GROUP));
				logMessage.setNbPostTraitement(matcher.group(DB2LoadLogsParserImpl.REGEX_NB_POST_TRAITEMENT_GROUP));

			}

		}
		return logMessage;
	}
}
