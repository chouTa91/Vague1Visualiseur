package fr.polemploi.suivi.migration.service.validator.impl;

import java.util.Arrays;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.polemploi.suivi.migration.entities.alert.Alert;
import fr.polemploi.suivi.migration.entities.alert.Tuple;
import fr.polemploi.suivi.migration.service.validator.AlertValidator;
import fr.polemploi.suivi.migration.service.validator.predicate.PredicateSuiviMigration;

@Component
public class AlertValidatorImpl implements AlertValidator {

	@Autowired
	private PredicateSuiviMigration predicateSuiviMigration;

	@Override
	public void dl1CheckMissingFileVague1(Alert alert) {

		Tuple<String, Predicate<Alert>> tuple = this.predicateSuiviMigration.dl1CheckMissingFileVague1(alert);

		alert.setAlertsMessages(Arrays.asList(tuple));
	}

	@Override
	public void dl1CheckVolumetrieVague1(Alert alert) {

		Tuple<String, Predicate<Alert>> tupleVague1 = this.predicateSuiviMigration.dl1CheckVolumetrieVague1(alert);
		Tuple<String, Predicate<Alert>> tupleEcart = this.predicateSuiviMigration.dl1CheckVolumetrieEcart(alert);

		alert.setAlertsMessages(Arrays.asList(tupleVague1, tupleEcart));
	}

}
