package fr.polemploi.suivi.migration.entities.alert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * TODO JBOU abstract, interface ou class ?
 *
 * @author jbourrea
 *
 */
public class Alert {

	private List<String> alertsMessages;

	public Alert() {
		this.alertsMessages = new ArrayList<>();
	}

	public List<String> getAlertsMessages() {
		return this.alertsMessages;
	}

	public void setAlertsMessages(List<Tuple<String, Predicate<Alert>>> tuple) {
		Objects.nonNull(tuple);

		for (Tuple<String, Predicate<Alert>> tp : tuple) {
			if (tp.getSecond().test(this)) {
				this.alertsMessages.add(tp.getFirst());
			}
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.alertsMessages);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Alert other)) {
			return false;
		}
		return Objects.equals(this.alertsMessages, other.alertsMessages);
	}

	@Override
	public String toString() {
		return "Alert [alertsMessages=" + this.alertsMessages + "]";
	}

}
