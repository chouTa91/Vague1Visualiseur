package fr.polemploi.suivi.migration.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class implémentant la gestion de la date de rafraichissement des données.
 *
 * @author jbourrea
 *
 */
public class DatedInfos {

	private String refreshDate;

	public DatedInfos() {
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMM uuuu HH:mm:ss");
		this.refreshDate = localDateTime.format(dateFormatter);
	}

	public String getRefreshDate() {
		return this.refreshDate;
	}

}
