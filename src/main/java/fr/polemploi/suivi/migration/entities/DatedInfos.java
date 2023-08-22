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

	private final String refreshDate;

	private Integer count;

	private Integer total;

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public DatedInfos() {
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d MMM uuuu HH:mm:ss");
		this.refreshDate = localDateTime.format(dateFormatter);
	}

	public String getRefreshDate() {
		return this.refreshDate;
	}

}
