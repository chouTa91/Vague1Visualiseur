package fr.polemploi.suivi.migration.service.validator.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesFiles;
import fr.polemploi.suivi.migration.entities.enums.common.StatusEnum;
import fr.polemploi.suivi.migration.entities.enums.error.ErrorsEnum;
import fr.polemploi.suivi.migration.entities.exception.TirErrors;
import fr.polemploi.suivi.migration.entities.message.logs.DB2ConvertMessage;
import fr.polemploi.suivi.migration.entities.message.logs.DB2LoadMessage;
import fr.polemploi.suivi.migration.entities.tir.TirDetailDB2;
import fr.polemploi.suivi.migration.entities.tir.TirDetailDL1;
import fr.polemploi.suivi.migration.service.validator.Validator;

@Component
public class ValidatorImpl implements Validator {

	@Override
	public List<TirErrors> getErrorsDL1(TirDetailDL1 tir) {

		List<TirErrors> errors = new ArrayList<>();

		errors.addAll(this.getDL1ControleErrors(tir));

		return errors;
	}

	@Override
	public List<TirErrors> getErrorsDB2(TirDetailDB2 tir) {

		List<TirErrors> errors = new ArrayList<>();

		errors.addAll(this.getDB2ControleErrors(tir));

		errors.addAll(this.getDB2ChargementErrors(tir));

		errors.addAll(this.getDB2ConversionErrors(tir));

		return errors;
	}

	/**
	 * Retourne la liste des erreur de la conversion DB2.
	 *
	 * @param tir
	 *            {@link TirDetailDL1} Le tir dont on veut extraire les erreurs.
	 * @return {@link List}<{@link TirErrors}> La liste des erreurs.
	 */
	private List<TirErrors> getDB2ConversionErrors(TirDetailDB2 tir) {

		List<TirErrors> errors = new ArrayList<>();

		for (DB2ConvertMessage logMessage : tir.getDb2conversion()) {
			if (logMessage.getStatus().equals(StatusEnum.KO)) {
				errors.add(new TirErrors(ErrorsEnum.DB2_CONVERSION, logMessage.getTableName()));

				break;
			}
		}

		return errors;
	}

	/**
	 * Retourne la liste des erreur du chargement de DB2.
	 *
	 * @param tir
	 *            {@link TirDetailDL1} Le tir dont on veut extraire les erreurs.
	 * @return {@link List}<{@link TirErrors}> La liste des erreurs.
	 */
	private List<TirErrors> getDB2ChargementErrors(TirDetailDB2 tir) {

		List<TirErrors> errors = new ArrayList<>();

		for (DB2LoadMessage logMessage : tir.getDb2chargement()) {
			if (logMessage.getStatus().equals(StatusEnum.KO)) {
				errors.add(new TirErrors(ErrorsEnum.DB2_CHARGEMENT, logMessage.getTable()));

				break;
			}
		}

		return errors;
	}

	/**
	 * Retourne la liste des erreur du contrôle des fichiers de DL1.
	 *
	 * @param tir
	 *            {@link TirDetailDL1} Le tir dont on veut extraire les erreurs.
	 * @return {@link List}<{@link TirErrors}> La liste des erreurs.
	 */
	private List<TirErrors> getDL1ControleErrors(TirDetailDL1 tir) {

		List<TirErrors> errors = new ArrayList<>();

		if (tir.getDl1ControleFilesContainer() != null) {
			for (DL1DirectionRegionaleTablesFiles drFiles : tir.getDl1ControleFilesContainer().getDl1Files()) {
				if (drFiles.hasMissingFiles()) {
					errors.add(new TirErrors(ErrorsEnum.DL1_CONTROLE_FICHIERS, drFiles.getCode()));
				}
			}
		}

		return errors;
	}

	/**
	 * Retourne la liste des erreurs du contrôle des fichiers de DB2.
	 *
	 * @param tir
	 *            {@link TirDetailDL1} Le tir dont on veut extraire les erreurs.
	 * @return {@link List}<{@link TirErrors}> La liste des erreurs.
	 */
	private List<TirErrors> getDB2ControleErrors(TirDetailDB2 tir) {

		List<TirErrors> errors = new ArrayList<>();

		// TODO JBOU remonter la DR qui n'est pas bonne pour le message d'erreru.
		if (tir.getDb2ControleFiles().hasMissingFiles()) {
			errors.add(new TirErrors(ErrorsEnum.DB2_CONTROLE_FICHIERS));
		}

		return errors;
	}

}
