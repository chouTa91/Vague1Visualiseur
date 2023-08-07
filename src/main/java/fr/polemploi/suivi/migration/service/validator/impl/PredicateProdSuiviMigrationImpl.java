package fr.polemploi.suivi.migration.service.validator.impl;

import java.util.Objects;
import java.util.function.Predicate;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import fr.polemploi.suivi.migration.entities.alert.Alert;
import fr.polemploi.suivi.migration.entities.alert.Tuple;
import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesFiles;
import fr.polemploi.suivi.migration.entities.dl1.Dl1Volumes;
import fr.polemploi.suivi.migration.service.validator.predicate.PredicateSuiviMigration;

/**
 * Implémentation de {@link PredicateSuiviMigration} par defaut.<br>
 * <b>Toute autre version d'outil non spécifiée.</b>
 *
 * @author jbourrea
 *
 */
@Component
@Profile("!22M04")
public class PredicateProdSuiviMigrationImpl implements PredicateSuiviMigration {

	@Override
	public Tuple<String, Predicate<Alert>> dl1CheckMissingFileVague1(Alert alert) {
		Objects.requireNonNull(alert);

		Predicate<Alert> pr = input -> ((DL1DirectionRegionaleTablesFiles) input).getHasMissingFileChefDeFile();

		return new Tuple<String, Predicate<Alert>>("Fichier non nécessaire pour le chef de file.", pr);
	}

	@Override
	public Tuple<String, Predicate<Alert>> dl1CheckVolumetrieVague1(Alert alert) {
		Objects.requireNonNull(alert);

		Predicate<Alert> pr = input -> !((Dl1Volumes) input).getIsVague1();

		return new Tuple<String, Predicate<Alert>>("Cette table ne fait pas partie de la vague 1", pr);
	}

	@Override
	public Tuple<String, Predicate<Alert>> dl1CheckVolumetrieEcart(Alert alert) {
		Objects.requireNonNull(alert);

		Predicate<Alert> pr = input -> (((Dl1Volumes) input).getVolumeLog()
				- ((Dl1Volumes) input).getVolumeOracle() < 10);

		return new Tuple<String, Predicate<Alert>>("L'écart entre les volumes n'est pas significatif", pr);
	}

}
