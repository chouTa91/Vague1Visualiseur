package fr.polemploi.suivi.migration.service.impl;

import java.io.IOException;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.polemploi.suivi.migration.api.counter.LogsCounter;
import fr.polemploi.suivi.migration.api.path.PathDispenser;
import fr.polemploi.suivi.migration.config.AppContext;
import fr.polemploi.suivi.migration.entities.db2.DB2TablesFiles;
import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesFiles;
import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesFilesContainer;
import fr.polemploi.suivi.migration.entities.dl1.Dl1Volumes;
import fr.polemploi.suivi.migration.entities.enums.common.DirectionRegionaleEnum;
import fr.polemploi.suivi.migration.entities.tir.TirDetailDB2;
import fr.polemploi.suivi.migration.entities.tir.TirDetailDL1;
import fr.polemploi.suivi.migration.entities.tir.TirSynthesis;
import fr.polemploi.suivi.migration.service.DRCompiler;
import fr.polemploi.suivi.migration.service.DataCompiler;
import fr.polemploi.suivi.migration.service.reader.LogsReader;
import fr.polemploi.suivi.migration.service.validator.AlertValidator;
import fr.polemploi.suivi.migration.service.validator.Validator;

@Service
public class DataCompilerImpl implements DataCompiler {

	@Autowired
	private AppContext appContext;

	@Autowired
	private LogsReader fileReader;

	@Autowired
	private LogsCounter logsCounter;

	@Autowired
	private PathDispenser pathDispenser;

	@Autowired
	private Validator validator;

	@Autowired
	private DRCompiler drCompiler;

	@Autowired
	private AlertValidator alertValidator;

	@Override
	public TirDetailDB2 gatherTirDB2AllInfos() throws IOException {

		TirDetailDB2 tir = new TirDetailDB2();

		tir.setDb2ControleFiles(this.logsCounter.getDB2MissingFilesFromDirectory());

		tir.addAllDb2chargement(this.fileReader.retrieveDB2LoadInfos(this.pathDispenser.getDB2LoadOracle()));

		tir.addAllDb2conversion(this.fileReader.retrieveDB2ConvertInfos(this.pathDispenser.getDB2ConvertOracle()));

		// tir.addAllDr2conversion(this.fileReader.retrieveFile(this.pathDispenser.getDB2ConversionAllFile()));

		tir.addAllErrors(this.validator.getErrorsDB2(tir));

		return tir;
	}

	@Override
	public TirDetailDL1 gatherTirDL1AllInfos() throws IOException {

		TirDetailDL1 tir = new TirDetailDL1();

		tir.setDl1ControleFilesContainer(this.logsCounter.getDL1MissingFilesFromDirectory());

		tir.setDl1ControleLogsContainer(this.logsCounter.getDL1MissingLogsFromDirectory());

		tir.setDL1TablesDetailsContainer(this.fileReader.retrieveDL1LogFiles(this.pathDispenser.getDL1NasFolder()));

		tir.setdL1TablesVolumeContainer(
				this.fileReader.retrieveDL1VolumeFile(this.pathDispenser.getDL1VolumetrieOracle()));

		tir.addAllErrors(this.validator.getErrorsDL1(tir));

		this.dl1ValidateAlert(tir);

		return tir;
	}

	/**
	 * Validation fine des erreurs remarques et precisions.
	 *
	 * @param tir
	 */
	private void dl1ValidateAlert(TirDetailDL1 tir) {
		for (DL1DirectionRegionaleTablesFiles fileInfos : tir.getDl1ControleFilesContainer().getDl1Files()) {
			this.alertValidator.dl1CheckMissingFileVague1(fileInfos);
		}

		for (DL1DirectionRegionaleTablesFiles fileInfos : tir.getDl1ControleLogsContainer().getDl1Files()) {
			this.alertValidator.dl1CheckMissingFileVague1(fileInfos);
		}

		for (Entry<String, Dl1Volumes> fileInfos : tir.getdL1TablesVolumeContainer().getVolume().entrySet()) {
			this.alertValidator.dl1CheckVolumetrieVague1(fileInfos.getValue());
		}
	}

	@Override
	public TirSynthesis gatherTirSynthesis() throws IOException {

		TirSynthesis tirSynthesis = new TirSynthesis();

		for (DirectionRegionaleEnum dr : DirectionRegionaleEnum.getDRByCible("Prod")) { // TODO JBOU Corriger avec le
																						// bon cadre, c'est ici en dur
																						// pour la démo.

			// On ne traite pas la DR00 ici.
			if (!dr.equals(DirectionRegionaleEnum.DR00)) {
				tirSynthesis.addDirectionSynthesis(this.drCompiler.gatherDRSynthesis(dr));
			}
		}

		return tirSynthesis;
	}

	@Override
	public DL1DirectionRegionaleTablesFilesContainer getDl1ControleFiles() {
		return this.logsCounter.getDL1MissingFilesFromDirectory();
	}

	@Override
	public DL1DirectionRegionaleTablesFilesContainer getDl1ControleLogs() {
		return this.logsCounter.getDL1MissingLogsFromDirectory();
	}

	@Override
	public DB2TablesFiles getDb2ControleFiles() {
		return this.logsCounter.getDB2MissingFilesFromDirectory();
	}

}
