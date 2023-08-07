package fr.polemploi.suivi.migration.entities.message.logs;

import java.util.Objects;

import org.springframework.util.StringUtils;

import fr.polemploi.suivi.migration.api.utils.TimeUtils;
import fr.polemploi.suivi.migration.entities.enums.common.StatusEnum;
import fr.polemploi.suivi.migration.entities.message.Message;

/**
 * Spécialisation d'un message en message de log.
 *
 * @author jbourrea
 *
 */
public class DB2LoadMessage extends Message<DB2LoadMessage> {

	private String table;
	private StatusEnum status;
	private Integer nbPreTraitement;
	private Integer nbPostTraitement;
	private String elapsedTime;

	public DB2LoadMessage() {
		super();
	}

	public String getTable() {
		return this.table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public Integer getNbPreTraitement() {
		return this.nbPreTraitement;
	}

	public void setNbPreTraitement(String nbPreTraitement) {

		if (StringUtils.hasLength(nbPreTraitement)) {
			this.nbPreTraitement = Integer.parseInt(nbPreTraitement, 10);
		} else {
			this.nbPreTraitement = 0;
		}
	}

	public Integer getNbPostTraitement() {
		return this.nbPostTraitement;
	}

	public void setNbPostTraitement(String nbPostTraitement) {

		if (StringUtils.hasLength(nbPostTraitement)) {
			this.nbPostTraitement = Integer.parseInt(nbPostTraitement, 10);
		} else {
			this.nbPostTraitement = 0;
		}
	}

	public StatusEnum getStatus() {
		return this.status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public String getElapsedTime() {
		return this.elapsedTime;
	}

	public void setElapsedTime(String elapsedTime) {
		this.elapsedTime = TimeUtils.formatFromStringSeconds(elapsedTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.elapsedTime, this.nbPostTraitement, this.nbPreTraitement, this.status, this.table);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof DB2LoadMessage other)) {
			return false;
		}
		return Objects.equals(this.elapsedTime, other.elapsedTime)
				&& Objects.equals(this.nbPostTraitement, other.nbPostTraitement)
				&& Objects.equals(this.nbPreTraitement, other.nbPreTraitement) && this.status == other.status
				&& Objects.equals(this.table, other.table);
	}

	@Override
	public String toString() {
		return "DB2LoadMessage [table=" + this.table + ", status=" + this.status + ", nbPreTraitement="
				+ this.nbPreTraitement + ", nbPostTraitement=" + this.nbPostTraitement + ", elapsedTime="
				+ this.elapsedTime + "]";
	}

}
