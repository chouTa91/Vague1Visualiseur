package fr.polemploi.suivi.migration.entities.enums.dl1;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumération des logs généré par le chargement de DL1.
 *
 * @author jbourrea
 *
 */
public enum DL1LogEnum {

	BR1_A2("BR1", "A2"),
	BR2_A2("BR2", "A2"),
	BR3_A2("BR3", "A2"),
	BR4_A2("BR4", "A2"),
	BR0_A4("BR0", "A4"),
	BR0_A7("BR0", "A7"),
	BR0_B6("BR0", "B6"),
	BR1_E1("BR1", "E1"),
	BR2_E1("BR2", "E1"),
	BR3_E1("BR3", "E1"),
	BR4_E1("BR4", "E1"),
	BR1_E2("BR1", "E2"),
	BR2_E2("BR2", "E2"),
	BR3_E2("BR3", "E2"),
	BR4_E2("BR4", "E2"),
	BR1_F1("BR1", "F1"),
	BR2_F1("BR2", "F1"),
	BR3_F1("BR3", "F1"),
	BR4_F1("BR4", "F1"),
	BR1_H1("BR1", "H1"),
	BR2_H1("BR2", "H1"),
	BR3_H1("BR3", "H1"),
	BR4_H1("BR4", "H1"),
	BR1_I1("BR1", "I1"),
	BR2_I1("BR2", "I1"),
	BR3_I1("BR3", "I1"),
	BR4_I1("BR4", "I1"),
	BR1_I2("BR1", "I2"),
	BR2_I2("BR2", "I2"),
	BR3_I2("BR3", "I2"),
	BR4_I2("BR4", "I2"),
	BR1_I3("BR1", "I3"),
	BR2_I3("BR2", "I3"),
	BR3_I3("BR3", "I3"),
	BR4_I3("BR4", "I3"),
	BR1_I4("BR1", "I4"),
	BR2_I4("BR2", "I4"),
	BR3_I4("BR3", "I4"),
	BR4_I4("BR4", "I4"),
	BR1_J1("BR1", "J1"),
	BR2_J1("BR2", "J1"),
	BR3_J1("BR3", "J1"),
	BR4_J1("BR4", "J1"),
	BR1_K1("BR1", "K1"),
	BR2_K1("BR2", "K1"),
	BR3_K1("BR3", "K1"),
	BR4_K1("BR4", "K1"),
	BR0_K2("BR0", "K2"),
	BR0_K3("BR0", "K3"),
	BR1_N1("BR1", "N1"),
	BR2_N1("BR2", "N1"),
	BR3_N1("BR3", "N1"),
	BR4_N1("BR4", "N1"),
	BR0_R3("BR0", "R3"),
	BR0_R4("BR0", "R4"),
	BR0_T1("BR0", "T1"),
	BR0_T2("BR0", "T2"),
	BR0_T3("BR0", "T3"),
	BR1_W1("BR1", "W1"),
	BR2_W1("BR2", "W1"),
	BR3_W1("BR3", "W1"),
	BR4_W1("BR4", "W1");

	private String branchname;
	private String tableName;

	private DL1LogEnum(String branchname, String segment) {
		this.branchname = branchname;
		this.tableName = segment;
	}

	public String getBranchName() {
		return this.branchname;
	}

	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String segment) {
		this.tableName = segment;
	}

	public static List<DL1LogEnum> getTablesByTableName(String tableName) {
		List<DL1LogEnum> results = new ArrayList<>();

		for (DL1LogEnum value : DL1LogEnum.values()) {
			if (value.getTableName().equals(tableName)) {
				results.add(value);
			}
		}

		return results;
	}

}
