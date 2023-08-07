package fr.polemploi.suivi.migration.api.parsers.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.polemploi.suivi.migration.api.parsers.DL1LogParser;
import fr.polemploi.suivi.migration.entities.enums.dl1.DL1TableEnum;
import fr.polemploi.suivi.migration.entities.message.logs.DL1LogMessage;

@Component
public class DL1LogsParserImpl implements DL1LogParser<DL1LogMessage> {

	private static final Logger logger = LoggerFactory.getLogger(DL1LogsParserImpl.class);

	@Override
	public Map<String, DL1LogMessage> parse(String message, List<DL1TableEnum> tables, boolean bySegment) {
		Objects.nonNull(message);
		Objects.nonNull(tables);

		Map<String, DL1LogMessage> messagesMap = new HashMap<>();

		InputStream inputStream = new ByteArrayInputStream(message.getBytes());

		try {

			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

			// Récupération de la table ciblée.
			String tableName = tables.get(0).getTableName();

			// On cherche via regex la ligne qui nous intéresse, exemple :
			// PK1A=00000039597
			Pattern pattern;
			Matcher matcher;
			pattern = Pattern.compile("^." + tableName + ".=\\d*");

			for (String line; (line = bufferedReader.readLine()) != null;) {

				DL1LogMessage dl1LogMessage = new DL1LogMessage();

				matcher = pattern.matcher(line);
				if (matcher.find()) {

					String[] splittedString = matcher.group().split("=");
					String tableKey;

					// if (!bySegment) {
					tableKey = splittedString[0].substring(0, 4);
					// } else {
					// tableKey = splittedString[0].substring(0, 3); // On split pour calculer sur
					// table en
					// // général pas que sur le segment.
					// }

					String volume = splittedString[1];

					// Si l'entrée existe déjà, on se contente d'ajouter la volumétrie des branches
					// suivante à l'existante pour avoir le total.
					if (messagesMap.get(tableKey) != null && messagesMap.get(tableKey).getTableName() != null) {
						messagesMap.get(tableKey).addVolume(new BigDecimal(volume));
					} else {
						dl1LogMessage.setTableName(tableKey);
						dl1LogMessage.addVolume(new BigDecimal(volume));
						messagesMap.put(tableKey, dl1LogMessage);
					}

				}

			}

			// On ferme le robinet des streams.
			bufferedReader.close();
			inputStream.close();

		} catch (IOException e) {
			DL1LogsParserImpl.logger.error("Log parsing error : ", e);
		}

		return messagesMap;
	}

}
