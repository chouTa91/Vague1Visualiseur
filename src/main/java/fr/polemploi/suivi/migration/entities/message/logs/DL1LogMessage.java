package fr.polemploi.suivi.migration.entities.message.logs;

import java.math.BigDecimal;
import java.util.Objects;

import fr.polemploi.suivi.migration.entities.message.Message;

/**
 * Représentation d'un message de log pour DL1.
 *
 * @author jbourrea
 *
 */
public class DL1LogMessage extends Message<DL1LogMessage> {

	private String tableName;

	private BigDecimal volume;

	public DL1LogMessage() {
		this.volume = new BigDecimal(0);
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public BigDecimal getVolume() {
		return this.volume;
	}

	public void addVolume(BigDecimal volume) {
		this.volume = this.volume.add(volume);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.tableName, this.volume);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof DL1LogMessage other)) {
			return false;
		}
		return Objects.equals(this.tableName, other.tableName) && Objects.equals(this.volume, other.volume);
	}

	@Override
	public String toString() {
		return "DL1LogMessage [tableName=" + this.tableName + ", volume=" + this.volume + "]";
	}

}
