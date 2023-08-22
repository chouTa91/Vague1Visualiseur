package fr.polemploi.suivi.migration.service.impl;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.polemploi.suivi.migration.entities.enums.db2.DB2TableEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.polemploi.suivi.migration.api.path.PathDispenser;
import fr.polemploi.suivi.migration.api.retriever.FileSearch;
import fr.polemploi.suivi.migration.api.retriever.FilesRetrieverApi;
import fr.polemploi.suivi.migration.config.AppContext;
import fr.polemploi.suivi.migration.entities.enums.common.DirectionRegionaleEnum;
import fr.polemploi.suivi.migration.entities.enums.dl1.DL1LogEnum;
import fr.polemploi.suivi.migration.entities.message.logs.RawFile;
import fr.polemploi.suivi.migration.service.FilesRetriever;

@Service
public class FilesRetrieverImpl implements FilesRetriever {

	private static final Logger logger = LoggerFactory.getLogger(FilesRetrieverImpl.class);

	@Autowired
	private FilesRetrieverApi filesRetrieverApi;

	@Autowired
	private AppContext appContext;

	@Autowired
	private PathDispenser pathDispenser;

	@Autowired
	private FileSearch fileSearch;

	@Override
	public List<RawFile> getDL1RawLogsFiles(String tableName) {
		Objects.requireNonNull(tableName);

		List<RawFile> results = new ArrayList<>();

		Map<String, Path> pathList = this.getPathList(tableName.substring(1, 3));

		for (Entry<String, Path> fileEntry : pathList.entrySet()) {
			RawFile rawFile = new RawFile();

			try {
				rawFile.setFileName(fileEntry.getKey());
				rawFile.setContent(this.filesRetrieverApi.retrieveFile(fileEntry.getValue()));
				results.add(rawFile);
			} catch (IOException e) {
				FilesRetrieverImpl.logger.error("Retrieving file from FS error : ", e);
			}

		}

		return results;
	}

	private Map<String, Path> getPathList(String tableName) {

		Map<String, Path> results = new HashMap<>();

		List<DL1LogEnum> tables = DL1LogEnum.getTablesByTableName(tableName);

		// Pour chaque DR du cadre dans le cas de la volumétrie total.
		for (DirectionRegionaleEnum direction : DirectionRegionaleEnum.getDRByCible(this.appContext.getCible())) {

			if (!direction.equals(DirectionRegionaleEnum.DR00)) {
				for (DL1LogEnum table : tables) {
					String fileName = this.guessDl1LogFileName(direction, table);

					results.put(fileName, Paths.get(this.pathDispenser.getDL1NasFolder() + fileName));
				}
			}
		}

		return results;
	}

	private String guessDl1LogFileName(DirectionRegionaleEnum direction, DL1LogEnum table) {
		// nom du fichier de log au format : log.A2.DR32.BR1

		StringBuilder sb = new StringBuilder();

		sb.append("log").append(".").append(table.getTableName()).append(".").append(direction.getCode()).append(".")
				.append(table.getBranchName());

		return sb.toString();
	}

	@Override
	public List<RawFile> getDL1RawLogsChargeFiles(String tableName) throws IOException {
		Objects.requireNonNull(tableName);

		List<RawFile> results = new ArrayList<>();

		Map<String, Path> pathList = this.getPathListCharge(tableName.substring(0, 4));

		for (Entry<String, Path> fileEntry : pathList.entrySet()) {
			RawFile rawFile = new RawFile();

			try {
				rawFile.setFileName(fileEntry.getKey());
				rawFile.setContent(this.filesRetrieverApi.retrieveFile(fileEntry.getValue()));
				results.add(rawFile);
			} catch (IOException e) {
				FilesRetrieverImpl.logger.error("Retrieving file from FS error : ", e);
			}

		}

		return results;
	}

	private Map<String, Path> getPathListCharge(String tableName) throws IOException {

		Map<String, Path> results = new HashMap<>();

		// Pour chaque DR du cadre dans le cas de la volumétrie total.
		for (DirectionRegionaleEnum direction : DirectionRegionaleEnum.getDRByCible(this.appContext.getCible())) {

			if (!direction.equals(DirectionRegionaleEnum.DR00)) {

				String fileName = this.guessDl1LogChargeFileName(direction, tableName);

				List<String> actualFiles = this.fileSearch
						.searchWithWc(Paths.get(this.pathDispenser.getDL1SchemaFolder() + tableName.substring(0, 3)
								+ FileSystems.getDefault().getSeparator() + "log"
								+ FileSystems.getDefault().getSeparator()), "glob:" + fileName);

				for (String actual : actualFiles) {
					results.put(actual,
							Paths.get(this.pathDispenser.getDL1SchemaFolder() + tableName.substring(0, 3)
									+ FileSystems.getDefault().getSeparator() + "log"
									+ FileSystems.getDefault().getSeparator() + actual));

				}

			}

		}

		return results;

	}

	private String guessDl1LogChargeFileName(DirectionRegionaleEnum direction, String tableName) {
		// nom du fichier de log au format : charge_PI2C323.log

		StringBuilder sb = new StringBuilder();

		// TODO JBOU joker character.
		sb.append("charge_").append(tableName).append("*.log");

		return sb.toString();
	}

	@Override
	public List<RawFile> getDL1RawLogsFilesTable(String tableName) throws IOException {
		Objects.nonNull(tableName);

		List<RawFile> results = new ArrayList<>();

		List<RawFile> rawFiles = this.getDL1RawLogsFiles(tableName);

		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile(tableName + "=\\d*\\W*\\n");

		if (!rawFiles.isEmpty()) {
			for (RawFile file : rawFiles) {

				matcher = pattern.matcher(file.getContent());

				if (matcher.find()) {
					results.add(new RawFile(file.getFileName(), matcher.group()));
				}
			}
		}

		return results;
	}

	public Path getDb2ConvertFilePath(String tabName){
		String fileName = Objects.requireNonNull(DB2TableEnum.getConvertLogFileNameByTableName(tabName));
		return Paths.get(this.pathDispenser.getDB2ConvertOracle() + fileName);
	}

	@Override
	public RawFile getDB2ConversionLogFile(String tableName) {
		RawFile result = new RawFile();
		Path filePath = getDb2ConvertFilePath(tableName);
		try {
			if(tableName.contains("."))
				tableName = tableName.split("\\.")[1];
			result = new RawFile(tableName, this.filesRetrieverApi.retrieveFile(filePath));
		} catch (IOException e) {
			FilesRetrieverImpl.logger.error("Retrieving file from FS error : ", e);
		}

		return result;
	}

	public Path getDb2LoadFilePath(String tabName){
		String fileName = Objects.requireNonNull(DB2TableEnum.getConvertLogFileNameByTableName(tabName));
		return Paths.get(this.pathDispenser.getDB2LoadOracle() + fileName);
	}

	@Override
	public RawFile getDB2LoadLogFile(String tableName) {
		RawFile result = new RawFile();
		Path filePath = getDb2LoadFilePath(tableName);
		try {
			if(tableName.contains("."))
				tableName = tableName.split("\\.")[1];
			result = new RawFile(tableName, this.filesRetrieverApi.retrieveFile(filePath));
		} catch (IOException e) {
			FilesRetrieverImpl.logger.error("Retrieving file from FS error : ", e);
		}

		return result;
	}

}