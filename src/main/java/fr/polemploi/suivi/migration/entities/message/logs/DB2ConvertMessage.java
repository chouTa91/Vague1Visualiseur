package fr.polemploi.suivi.migration.entities.message.logs;

import java.util.Objects;

import fr.polemploi.suivi.migration.api.utils.TimeUtils;
import fr.polemploi.suivi.migration.entities.enums.common.StatusEnum;
import fr.polemploi.suivi.migration.entities.message.Message;

/**
 * POJO représentant un log de conversion de données pour DB2.
 *
 * @author jbourrea
 *
 */
public class DB2ConvertMessage extends Message<DB2ConvertMessage> {

	private String tableName;
	private Integer nbRecProcessed = 0;
	private Integer nbRecsRead = 0;
	private Integer nbRecsWritten = 0;
	private Integer maxInputRecLength = 0;
	private Integer maxInputRecLengthLF = 0;
	private String elapsedTime;

	private StatusEnum status;

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getNbRecProcessed() {
		return this.nbRecProcessed;
	}

	public void setNbRecProcessed(Integer nbRecProcessed) {
		this.nbRecProcessed = nbRecProcessed;
	}

	public Integer getNbRecsRead() {
		return this.nbRecsRead;
	}

	public void setNbRecsRead(Integer nbRecsRead) {
		this.nbRecsRead = nbRecsRead;
	}

	public Integer getNbRecsWritten() {
		return this.nbRecsWritten;
	}

	public void setNbRecsWritten(Integer nbRecsWritten) {
		this.nbRecsWritten = nbRecsWritten;
	}

	public Integer getMaxInputRecLength() {
		return this.maxInputRecLength;
	}

	public void setMaxInputRecLength(Integer maxInputRecLength) {
		this.maxInputRecLength = maxInputRecLength;
	}

	public Integer getMaxInputRecLengthLF() {
		return this.maxInputRecLengthLF;
	}

	public void setMaxInputRecLengthLF(Integer maxInputRecLengthLF) {
		this.maxInputRecLengthLF = maxInputRecLengthLF;
	}

	public String getElapsedTime() {
		return this.elapsedTime;
	}

	public void setElapsedTime(String elapsedTime) {
		this.elapsedTime = TimeUtils.formatFromStringSeconds(elapsedTime);
	}

	public StatusEnum getStatus() {
		return this.nbRecsRead.equals(this.nbRecsWritten) ? StatusEnum.OK : StatusEnum.KO;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.elapsedTime, this.maxInputRecLength, this.maxInputRecLengthLF, this.nbRecProcessed,
				this.nbRecsRead, this.nbRecsWritten, this.status, this.tableName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof DB2ConvertMessage other)) {
			return false;
		}
		return Objects.equals(this.elapsedTime, other.elapsedTime)
				&& Objects.equals(this.maxInputRecLength, other.maxInputRecLength)
				&& Objects.equals(this.maxInputRecLengthLF, other.maxInputRecLengthLF)
				&& Objects.equals(this.nbRecProcessed, other.nbRecProcessed)
				&& Objects.equals(this.nbRecsRead, other.nbRecsRead)
				&& Objects.equals(this.nbRecsWritten, other.nbRecsWritten) && this.status == other.status
				&& Objects.equals(this.tableName, other.tableName);
	}

	@Override
	public String toString() {
		return "DB2ConvertMessage [tableName=" + this.tableName + ", nbRecProcessed=" + this.nbRecProcessed
				+ ", nbRecsRead=" + this.nbRecsRead + ", nbRecsWritten=" + this.nbRecsWritten + ", maxInputRecLength="
				+ this.maxInputRecLength + ", maxInputRecLengthLF=" + this.maxInputRecLengthLF + ", elapsedTime="
				+ this.elapsedTime + ", status=" + this.status + "]";
	}

}
