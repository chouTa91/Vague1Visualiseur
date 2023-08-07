package fr.polemploi.suivi.migration.service.reader.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import fr.polemploi.suivi.migration.api.parsers.DL1LogParser;
import fr.polemploi.suivi.migration.api.parsers.LogsParser;
import fr.polemploi.suivi.migration.api.path.PathDispenser;
import fr.polemploi.suivi.migration.config.AppContext;
import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesDetails;
import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesDetailsContainer;
import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesVolumeContainer;
import fr.polemploi.suivi.migration.entities.dl1.Dl1Volumes;
import fr.polemploi.suivi.migration.entities.enums.common.DirectionRegionaleEnum;
import fr.polemploi.suivi.migration.entities.enums.dl1.DL1TableEnum;
import fr.polemploi.suivi.migration.entities.message.logs.DB2ConvertMessage;
import fr.polemploi.suivi.migration.entities.message.logs.DB2LoadMessage;
import fr.polemploi.suivi.migration.entities.message.logs.DL1LogMessage;
import fr.polemploi.suivi.migration.service.reader.LogsReader;

@Service
public class LogsReaderImpl implements LogsReader {

	private static final Logger logger = LoggerFactory.getLogger(LogsReaderImpl.class);

	@Autowired
	private PathDispenser pathDispenser;

	@Autowired
	private AppContext appContext;

	@Autowired
	private LogsParser<DB2LoadMessage> logsLoadParser;

	@Autowired
	private LogsParser<DB2ConvertMessage> logsConvertParser;

	@Autowired
	private DL1LogParser<DL1LogMessage> dl1LogsParser;

	@Override
	public List<DB2LoadMessage> retrieveDB2LoadInfos(String path) throws IOException {

		return this.logsLoadParser.parseAll(path);
	}

	@Override
	public List<DB2ConvertMessage> retrieveDB2ConvertInfos(String path) throws IOException {

		return this.logsConvertParser.parseAll(path);
	}

	@Override
	public Map<String, DL1LogMessage> retrieveDL1LogFilesBySegment(String folderPath) throws IOException {

		Map<String, DL1LogMessage> results = new HashMap<>();

		// On va boucler sur chaque DR pour en analyser les logs par table.
		for (DirectionRegionaleEnum dr : DirectionRegionaleEnum.getDRByCible(this.appContext.getCible())) {

			if (!dr.equals(DirectionRegionaleEnum.DR00)) {

				// Retrouve chaque fichier que l'on va lire.
				// Exemple de nom de fichier : log.A2.DR32.BR4.
				Map<String, DL1LogMessage> mapTables = new HashMap<>();

				this.processAllTables(folderPath, dr, mapTables, true);

				for (Entry<String, DL1LogMessage> entry : mapTables.entrySet()) {
					DL1LogMessage entryDr = results.get(entry.getKey());

					if (entryDr != null) {
						results.get(entry.getKey()).addVolume(entry.getValue().getVolume());
					} else {
						results.put(entry.getKey(), entry.getValue());
					}

				}

			}
		}

		return results;
	}

	@Override
	public DL1DirectionRegionaleTablesDetailsContainer retrieveDL1LogFiles(String folderPath) throws IOException {

		DL1DirectionRegionaleTablesDetailsContainer results = new DL1DirectionRegionaleTablesDetailsContainer();

		// On va boucler sur chaque DR pour en analyser les logs par table.
		for (DirectionRegionaleEnum dr : DirectionRegionaleEnum.getDRByCible(this.appContext.getCible())) {

			if (!dr.equals(DirectionRegionaleEnum.DR00)) {

				DL1DirectionRegionaleTablesDetails drDetails = new DL1DirectionRegionaleTablesDetails(dr.getCode());

				// Retrouve chaque fichier que l'on va lire.
				// Exemple de nom de fichier : log.A2.DR32.BR4.
				Map<String, DL1LogMessage> mapTables = new HashMap<>();

				this.processAllTables(folderPath, dr, mapTables, false);

				drDetails.putAllLogInfo(mapTables);
				results.addDl1Details(drDetails);

			}
		}

		return results;
	}

	private void processAllTables(String folderPath, DirectionRegionaleEnum dr, Map<String, DL1LogMessage> mapTables,
			boolean bySegment) throws IOException, FileNotFoundException {

		for (DL1TableEnum tables : DL1TableEnum.getA2TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getA2Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getA4TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getA4Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getA7TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getA7Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getB6TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getB6Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getE1TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getE1Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getE2TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getE2Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getF1TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getF1Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getH1TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getH1Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getI1TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getI1Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getI2TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getI2Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getI3TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getI3Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getI4TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getI4Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getJ1TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getJ1Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getK1TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getK1Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getK2TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getK2Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getK3TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getK3Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getN1TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getN1Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getR3TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getR3Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getR4TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getR4Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getT1TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getT1Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getT2TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getT2Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getT3TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getT3Tables(), bySegment);
		}

		for (DL1TableEnum tables : DL1TableEnum.getW1TablesByBranches()) {

			this.processByTable(folderPath, dr, mapTables, tables, DL1TableEnum.getW1Tables(), bySegment);
		}

	}

	/**
	 * Calcul de la volumétrie par table.
	 *
	 * @param folderPath
	 * @param dr
	 * @param mapTables
	 * @param tablesBranches
	 * @param tables
	 * @param bySegment
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private void processByTable(String folderPath, DirectionRegionaleEnum dr, Map<String, DL1LogMessage> mapTables,
			DL1TableEnum tablesBranches, List<DL1TableEnum> tables, boolean bySegment)
			throws IOException, FileNotFoundException {

		FileSystemResource file = new FileSystemResource(folderPath + "log." + tablesBranches.getTableName() + "."
				+ dr.getCode() + "." + tablesBranches.getBranchName());

		if (file.getFile().exists()) {

			Map<String, DL1LogMessage> mapResult = this.dl1LogsParser.parse(
					StreamUtils.copyToString(file.getInputStream(), Charset.defaultCharset()), tables, bySegment);

			// Calcul du total des entrées.
			for (Entry<String, DL1LogMessage> entry : mapResult.entrySet()) {
				// Si l'entré existe, on additionne, autrement, on créée l'entrée.
				if (mapTables.get(entry.getKey()) != null) {
					mapTables.get(entry.getKey()).addVolume(entry.getValue().getVolume());
				} else {
					mapTables.put(entry.getKey(), entry.getValue());
				}
			}

		} else {
			LogsReaderImpl.logger.error("File not found : {}", file.getPath());
		}
	}

	// TODO JBOU dans une api différente ? Etude pour faire un count(*) à la volée.
	@Override
	public DL1DirectionRegionaleTablesVolumeContainer retrieveDL1VolumeFile(String volumetrieOraclePath) {

		DL1DirectionRegionaleTablesVolumeContainer dl1DirectionRegionaleTablesVolumeContainer = new DL1DirectionRegionaleTablesVolumeContainer();

		FileSystemResource file = new FileSystemResource(volumetrieOraclePath);

		if (file.getFile().exists()) {
			BufferedReader bufferedReader;

			Pattern pattern;
			Matcher matcher;
			pattern = Pattern.compile("----------");

			try {
				bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));

				String previousLine = "";
				boolean match = false;
				String tableName = "";
				String volume = "0";

				for (String line; (line = bufferedReader.readLine()) != null;) {

					matcher = pattern.matcher(line);

					// Si match est vrai, c'est que l'on est sur la ligne de la valeur
					// volulmétrique.
					if (match) {
						volume = line;
					}

					if (matcher.find()) {

						// Quand y'a match, on mets de coté la ligne précédetne qui contient le nom de
						// la table.
						tableName = previousLine;
						match = true;

					} else {

						match = false;
						previousLine = line;
					}

					if (!Strings.isBlank(tableName)) {
						Dl1Volumes dl1Volume = new Dl1Volumes();
						dl1Volume.setVolumeOracle(Integer.parseInt(volume.trim()));

						dl1DirectionRegionaleTablesVolumeContainer.putVolume(tableName.trim(), dl1Volume);
					}

				}

				// Récupération des resultat depuis les logs.
				Map<String, DL1LogMessage> resultsFromLogs = this
						.retrieveDL1LogFilesBySegment(this.pathDispenser.getDL1NasFolder());

				for (Entry<String, DL1LogMessage> entry : resultsFromLogs.entrySet()) {

					Dl1Volumes entryValue = dl1DirectionRegionaleTablesVolumeContainer.getVolume().get(entry.getKey());
					if (entryValue != null) {
						dl1DirectionRegionaleTablesVolumeContainer.getVolume().get(entry.getKey())
								.setVolumeLog(entry.getValue().getVolume().intValue());

					} else { // Si la table n'existe pas dans le count de Oracle.
						entryValue = new Dl1Volumes();
						entryValue.setVolumeLog(entry.getValue().getVolume().intValue());
						dl1DirectionRegionaleTablesVolumeContainer.putVolume(entry.getKey(), entryValue);

					}
				}

				bufferedReader.close();

				this.flagVagueIntegration(dl1DirectionRegionaleTablesVolumeContainer);

			} catch (IOException e) {
				LogsReaderImpl.logger.error("Oracle data volume file counldn't be found : ", e);
			}
		}

		return dl1DirectionRegionaleTablesVolumeContainer;
	}

	/**
	 * En fonction de la table on vut savoir si elle fait parti d'une certaine vague
	 * poiur savoir si es alertes sont justifiées.
	 *
	 * @param dl1DirectionRegionaleTablesVolumeContainer
	 */
	private void flagVagueIntegration(
			DL1DirectionRegionaleTablesVolumeContainer dl1DirectionRegionaleTablesVolumeContainer) {

		for (Entry<String, Dl1Volumes> entry : dl1DirectionRegionaleTablesVolumeContainer.getVolume().entrySet()) {

			Dl1Volumes entryValue = dl1DirectionRegionaleTablesVolumeContainer.getVolume().get(entry.getKey());

			List<DL1TableEnum> vague = DL1TableEnum.getTablesByFullName(entry.getKey());

			if (vague.size() > 0 && vague.get(0).getVague().equals("vague1")) {
				entryValue.setVague1(true);
			}
		}
	}

}
