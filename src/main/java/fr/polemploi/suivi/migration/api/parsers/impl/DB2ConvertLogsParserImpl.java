package fr.polemploi.suivi.migration.api.parsers.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import fr.polemploi.suivi.migration.api.parsers.LogsParser;
import fr.polemploi.suivi.migration.entities.enums.db2.DB2TableEnum;
import fr.polemploi.suivi.migration.entities.message.logs.DB2ConvertMessage;

@Component
public class DB2ConvertLogsParserImpl implements LogsParser<DB2ConvertMessage> {

	private static final Logger logger = LoggerFactory.getLogger(DB2ConvertLogsParserImpl.class);

	@Override
	public List<DB2ConvertMessage> parseAll(String path) {

		List<DB2ConvertMessage> messages = new ArrayList<>();

		for (DB2TableEnum table : DB2TableEnum.getDB2Tables()) {

			// Format du nom de fichier : P0101B_DR00.log
			FileSystemResource file = new FileSystemResource(path + table.getTableName() + "_DR00.log");

			if (file.getFile().exists()) {

				try {

					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(file.getInputStream(), "UTF-8"));

					DB2ConvertMessage logMessage = this.readDetails(file.getContentAsString(Charset.defaultCharset()));

					messages.add(logMessage);

					// On ferme le robinet des streams.
					bufferedReader.close();

				} catch (IOException e) {
					DB2ConvertLogsParserImpl.logger.error("Log parsing error : ", e);
				}
			}
		}

		return messages;
	}

	private DB2ConvertMessage readDetails(String fileContent) throws NumberFormatException, IOException {

		DB2ConvertMessage logMessage = new DB2ConvertMessage();

		// On cherche via regex la ligne qui nous intéresse, exemple :
		// Elapsed Time: 770 seconds
		Pattern pattern;
		Matcher matcher;

		pattern = Pattern.compile("(?<tableName>\\w*\\.\\w*)");
		matcher = pattern.matcher(fileContent);
		if (matcher.find()) {
			logMessage.setTableName(matcher.group("tableName"));
		}

		pattern = Pattern.compile("Elapsed Time:.(?<elapsedTime>\\d*)");
		matcher = pattern.matcher(fileContent);
		if (matcher.find()) {
			logMessage.setElapsedTime(matcher.group("elapsedTime"));
		}

		pattern = Pattern.compile("nb rec processed:.(?<nbRecProcessed>\\d*)");
		matcher = pattern.matcher(fileContent);
		if (matcher.find()) {
			logMessage.setNbRecProcessed(Integer.valueOf(matcher.group("nbRecProcessed")));
		}

		pattern = Pattern.compile("nb recs read   :.(?<nbRecsRead>\\d*)");
		matcher = pattern.matcher(fileContent);
		if (matcher.find()) {
			logMessage.setNbRecsRead(Integer.valueOf(matcher.group("nbRecsRead")));
		}

		pattern = Pattern.compile("nb recs written:.(?<nbRecsWritten>\\d*)");
		matcher = pattern.matcher(fileContent);
		if (matcher.find()) {
			logMessage.setNbRecsWritten(Integer.valueOf(matcher.group("nbRecsWritten")));
		}

		pattern = Pattern.compile("Max input rec Len:.(?<maxInputRecLen>\\d*)");
		matcher = pattern.matcher(fileContent);
		if (matcher.find()) {
			logMessage.setMaxInputRecLength(Integer.valueOf(matcher.group("maxInputRecLen")));
		}

		pattern = Pattern.compile("Max Output rec Len\\(including LF\\):.(?<maxInputRecLengthLF>\\d*)");
		matcher = pattern.matcher(fileContent);
		if (matcher.find()) {
			logMessage.setMaxInputRecLengthLF(Integer.valueOf(matcher.group("maxInputRecLengthLF")));
		}

		return logMessage;
	}

}
