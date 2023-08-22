package fr.polemploi.suivi.migration.entities.db2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.polemploi.suivi.migration.entities.DatedInfos;

/**
 * POJO du contrôle des fichiers livrés pour DB2.
 *
 * @author jbourrea
 *
 */
public class DB2TablesFiles extends DatedInfos {

	private final List<String> missingFiles;

	public DB2TablesFiles() {
		super();
		this.missingFiles = new ArrayList<>();
	}

	public List<String> getMissingFiles() {
		return this.missingFiles;
	}

	public void addMissingFile(String missingFiles) {
		this.missingFiles.add(missingFiles);
	}

	public boolean hasMissingFiles() {
		return !this.missingFiles.isEmpty();
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getCount(), this.missingFiles);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof DB2TablesFiles other)) {
			return false;
		}
		return Objects.equals(this.getCount(), other.getCount()) && Objects.equals(this.missingFiles, other.missingFiles);
	}

	@Override
	public String toString() {
		return "DB2TablesFiles [count=" + this.getCount() + ", missingFiles=" + this.missingFiles + "]";
	}

}
