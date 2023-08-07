package fr.polemploi.suivi.migration.entities.tir;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.polemploi.suivi.migration.entities.db2.DB2TablesFiles;
import fr.polemploi.suivi.migration.entities.message.logs.DB2ConvertMessage;
import fr.polemploi.suivi.migration.entities.message.logs.DB2LoadMessage;

/**
 * POJO représentant un tir.
 *
 * TODO JBOU spécialisation DL1 et DB2.
 *
 * @author jbourrea
 *
 */
public class TirDetailDB2 extends Tir {

	private DB2TablesFiles db2ControleFiles;

	private List<DB2LoadMessage> dr2chargement;

	private List<DB2ConvertMessage> db2conversion;

	public TirDetailDB2() {
		super();
		this.dr2chargement = new ArrayList<>();
		this.db2conversion = new ArrayList<>();
	}

	public DB2TablesFiles getDb2ControleFiles() {
		return this.db2ControleFiles;
	}

	public void setDb2ControleFiles(DB2TablesFiles db2ControleFiles) {
		this.db2ControleFiles = db2ControleFiles;
	}

	public List<DB2LoadMessage> getDr2chargement() {
		return this.dr2chargement;
	}

	public void addAllDr2chargement(List<DB2LoadMessage> dr2chargement) {
		this.dr2chargement.addAll(dr2chargement);
	}

	public List<DB2ConvertMessage> getDb2conversion() {
		return this.db2conversion;
	}

	public void addAllDb2conversion(List<DB2ConvertMessage> dr2conversion) {
		this.db2conversion.addAll(dr2conversion);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(this.db2ControleFiles, this.db2conversion, this.dr2chargement);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || !(obj instanceof TirDetailDB2 other)) {
			return false;
		}
		return Objects.equals(this.db2ControleFiles, other.db2ControleFiles)
				&& Objects.equals(this.db2conversion, other.db2conversion)
				&& Objects.equals(this.dr2chargement, other.dr2chargement);
	}

	@Override
	public String toString() {
		return "TirDetailDB2 [db2ControleFiles=" + this.db2ControleFiles + ", dr2chargement=" + this.dr2chargement
				+ ", db2conversion=" + this.db2conversion + "]";
	}

}
