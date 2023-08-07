package fr.polemploi.suivi.migration.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.polemploi.suivi.migration.api.counter.LogsCounter;
import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesFiles;
import fr.polemploi.suivi.migration.entities.enums.common.DirectionRegionaleEnum;
import fr.polemploi.suivi.migration.entities.enums.common.StatusEnum;
import fr.polemploi.suivi.migration.entities.tir.DRSynthesis;
import fr.polemploi.suivi.migration.service.DRCompiler;

@Service
public class DRCompilerImpl implements DRCompiler {

	@Autowired
	private LogsCounter logCounter;

	@Override
	public DRSynthesis gatherDRSynthesis(DirectionRegionaleEnum dr) {

		DRSynthesis drSynthesis = new DRSynthesis();

		drSynthesis.setDirection(dr);

		DL1DirectionRegionaleTablesFiles infosDr = this.logCounter.getDL1MissingFilesForDirection(dr);

		if (infosDr.hasMissingFiles()) {
			drSynthesis.setStatus(StatusEnum.KO);
		} else {
			drSynthesis.setStatus(StatusEnum.OK);
		}

		return drSynthesis;
	}

}
