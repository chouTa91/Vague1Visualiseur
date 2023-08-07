package fr.polemploi.suivi.migration.entities.dl1;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import fr.polemploi.suivi.migration.entities.message.logs.DL1LogMessage;

/**
 * POJO du détails des fichiers de logs de DL1.
 *
 * @author jbourrea
 *
 */
public class DL1DirectionRegionaleTablesDetails {

	private String code;

	/**
	 * <b>Key</b> : nom de la table.<br>
	 * <b>Value</b> : information de la table.
	 */
	Map<String, DL1LogMessage> logInfos;

	// TODO JBOU total des resultats par DR.

	public DL1DirectionRegionaleTablesDetails(String code) {
		this.code = code;
		this.logInfos = new HashMap<>();
	}

	public String getCode() {
		return this.code;
	}

	public Map<String, DL1LogMessage> getLogInfos() {
		return this.logInfos;
	}

	public void putLogInfo(String tableName, DL1LogMessage logInfo) {
		this.logInfos.put(tableName, logInfo);
	}

	public void putAllLogInfo(Map<String, DL1LogMessage> map) {
		this.logInfos.putAll(map);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.code, this.logInfos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof DL1DirectionRegionaleTablesDetails other)) {
			return false;
		}
		return Objects.equals(this.code, other.code) && Objects.equals(this.logInfos, other.logInfos);
	}

	@Override
	public String toString() {
		return "DL1DirectionRegionaleTablesDetails [code=" + this.code + ", logInfos=" + this.logInfos + "]";
	}

}
