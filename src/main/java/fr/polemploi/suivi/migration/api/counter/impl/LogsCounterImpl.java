package fr.polemploi.suivi.migration.api.counter.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.polemploi.suivi.migration.api.counter.LogsCounter;
import fr.polemploi.suivi.migration.api.path.PathDispenser;
import fr.polemploi.suivi.migration.config.AppContext;
import fr.polemploi.suivi.migration.entities.db2.DB2TablesFiles;
import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesFiles;
import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesFilesContainer;
import fr.polemploi.suivi.migration.entities.enums.common.DirectionRegionaleEnum;
import fr.polemploi.suivi.migration.entities.enums.db2.DB2TableEnum;
import fr.polemploi.suivi.migration.entities.enums.dl1.DL1LogEnum;
import fr.polemploi.suivi.migration.entities.enums.dl1.DL1TableEnum;

@Component
public class LogsCounterImpl implements LogsCounter {

	private static final String DOT_SEPARATOR = ".";
	private static final String TAB_PREFIX_STR = "Tab";

	@Autowired
	private AppContext appContext;

	@Autowired
	private PathDispenser pathDispenser;

	@Override
	public DL1DirectionRegionaleTablesFilesContainer getDL1MissingFilesFromDirectory() {

		DL1DirectionRegionaleTablesFilesContainer dl1DirectionRegionaleFilesList = new DL1DirectionRegionaleTablesFilesContainer();

		// On va vérifier pour chaque DR que leurs fichiers de données de DL1 sont
		// présents, s'il ne le sont pas, on veut le savoir !

		for (DirectionRegionaleEnum directionRegionale : DirectionRegionaleEnum
				.getDRByCible(this.appContext.getCible())) {

			// On ne traite pas la DR00 ici.
			if (!directionRegionale.equals(DirectionRegionaleEnum.DR00)) {

				DL1DirectionRegionaleTablesFiles dl1DirectionRegionaleFiles = this
						.getDL1MissingFilesForDirection(directionRegionale);

				dl1DirectionRegionaleFilesList.addDl1Files(dl1DirectionRegionaleFiles);
			}
		}

		return dl1DirectionRegionaleFilesList;
	}

	@Override
	public DL1DirectionRegionaleTablesFiles getDL1MissingFilesForDirection(DirectionRegionaleEnum directionRegionale) {
		DL1DirectionRegionaleTablesFiles dl1DirectionRegionaleFiles = new DL1DirectionRegionaleTablesFiles();
		dl1DirectionRegionaleFiles.setCode(directionRegionale.getCode());
		Integer count = 0; // Compteur de fichiers livrés.

		for (DL1TableEnum table : DL1TableEnum.values()) {
			String filename = table.getFullName() + LogsCounterImpl.DOT_SEPARATOR + directionRegionale.getCode()
					+ LogsCounterImpl.DOT_SEPARATOR + table.getBranchName();

			File deliveredFile = new File(this.pathDispenser.getDL1NasFolder() + filename);

			// Si pour la DR en cours, on ne trouve pas le fichier qui correspond à
			// l'énumeration des tables DL1, on est en erreur.
			if (deliveredFile.isFile() && deliveredFile.exists()) {
				count++;
			} else {

				dl1DirectionRegionaleFiles.addMissingFile(filename);

				// TODO JBOU énumération des caractéristiques pour les chef de file.
				if (!directionRegionale.equals(this.appContext.getChefDeFile()) && filename.startsWith("PA7A")) {
					dl1DirectionRegionaleFiles.setHasMissingFile(true);
				} else {
					dl1DirectionRegionaleFiles.setHasMissingFileChefDeFile(true);
				}
			}

		}

		dl1DirectionRegionaleFiles.setCount(count);
		return dl1DirectionRegionaleFiles;

	}

	@Override
	public DB2TablesFiles getDB2MissingFilesFromDirectory() {

		// On va vérifier que les fichiers de données de DB2 sont présents,
		// s'il ne le sont pas, on veut le savoir !

		// Il n'y a pas de traitement spécifique aux directions générales, c'est un
		// traitement globale.

		DB2TablesFiles db2TablesFiles = new DB2TablesFiles();
		Integer count = 0; // Compteur de fichiers livrés.

		for (DB2TableEnum table : DB2TableEnum.values()) {

			// Exemple de nom de fichier DB2 : Tab.S.P2801A.DR00
			String filename = LogsCounterImpl.TAB_PREFIX_STR + LogsCounterImpl.DOT_SEPARATOR + table.getBranchName()
					+ LogsCounterImpl.DOT_SEPARATOR + table.getTableName() + LogsCounterImpl.DOT_SEPARATOR
					+ DirectionRegionaleEnum.DR00.getCode();

			File deliveredFile = new File(this.pathDispenser.getDB2RootFolder() + filename);

			// Si pour la DR en cours, on ne trouve pas le fichier qui correspond à
			// l'énumeration des tables DB2, on est en erreur.
			if (deliveredFile.isFile() && deliveredFile.exists()) {
				count++;
			} else {
				db2TablesFiles.addMissingFile(filename);
			}

		}

		db2TablesFiles.setCount(count);

		return db2TablesFiles;
	}

	@Override
	public DL1DirectionRegionaleTablesFilesContainer getDL1MissingLogsFromDirectory() {

		DL1DirectionRegionaleTablesFilesContainer dl1DirectionRegionaleFilesList = new DL1DirectionRegionaleTablesFilesContainer();

		// On va vérifier pour chaque DR que leurs fichiers de données de DL1 sont
		// présents, s'il ne le sont pas, on veut le savoir !

		for (DirectionRegionaleEnum directionRegionale : DirectionRegionaleEnum
				.getDRByCible(this.appContext.getCible())) {

			// On ne traite pas la DR00 ici.
			if (!directionRegionale.equals(DirectionRegionaleEnum.DR00)) {

				DL1DirectionRegionaleTablesFiles dl1DirectionRegionaleFiles = new DL1DirectionRegionaleTablesFiles();
				dl1DirectionRegionaleFiles.setCode(directionRegionale.getCode());
				Integer count = 0; // Compteur de fichiers livrés.

				for (DL1LogEnum table : DL1LogEnum.values()) {

					String filename = "log" + LogsCounterImpl.DOT_SEPARATOR + table.getTableName()
							+ LogsCounterImpl.DOT_SEPARATOR + directionRegionale.getCode()
							+ LogsCounterImpl.DOT_SEPARATOR + table.getBranchName();

					File deliveredFile = new File(this.pathDispenser.getDL1NasFolder() + filename);

					// Si pour la DR en cours, on ne trouve pas le fichier qui correspond à
					// l'énumeration des tables DL1, on est en erreur.
					if (deliveredFile.isFile() && deliveredFile.exists()) {
						count++;
					} else {
						// TODO JBOU énumération des caractéristiques pour les chef de file.

						dl1DirectionRegionaleFiles.addMissingFile(filename);

						if (!(directionRegionale.equals(this.appContext.getChefDeFile())
								&& filename.startsWith("log.A7"))) {
							dl1DirectionRegionaleFilesList.setHasMissingFile(true);
						} else {
							dl1DirectionRegionaleFiles.setHasMissingFileChefDeFile(true);
						}
					}

				}

				dl1DirectionRegionaleFiles.setCount(count);
				dl1DirectionRegionaleFilesList.addDl1Files(dl1DirectionRegionaleFiles);
			}
		}

		return dl1DirectionRegionaleFilesList;
	}

}
