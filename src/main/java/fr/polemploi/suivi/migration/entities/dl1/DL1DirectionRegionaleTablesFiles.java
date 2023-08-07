package fr.polemploi.suivi.migration.entities.dl1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.polemploi.suivi.migration.entities.alert.Alert;

/**
 * POJO du contrôle des fichiers livrés pour DL1.
 *
 * @author jbourrea
 *
 */
public class DL1DirectionRegionaleTablesFiles extends Alert {

	private String code;

	private Integer count;

	private List<String> missingFiles;

	private boolean hasMissingFile = false;

	private boolean hasMissingFileChefDeFile = false;

	public DL1DirectionRegionaleTablesFiles() {
		super();
		this.missingFiles = new ArrayList<>();
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
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

	public boolean getHasMissingFile() {
		return this.hasMissingFile;
	}

	public void setHasMissingFile(boolean hasMissingFile) {
		this.hasMissingFile = hasMissingFile;
	}

	public boolean getHasMissingFileChefDeFile() {
		return this.hasMissingFileChefDeFile;
	}

	public void setHasMissingFileChefDeFile(boolean hasMissingFileChefDeFile) {
		this.hasMissingFileChefDeFile = hasMissingFileChefDeFile;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(this.code, this.count, this.hasMissingFile,
				this.hasMissingFileChefDeFile, this.missingFiles);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || !(obj instanceof DL1DirectionRegionaleTablesFiles other)) {
			return false;
		}
		return Objects.equals(this.code, other.code) && Objects.equals(this.count, other.count)
				&& this.hasMissingFile == other.hasMissingFile
				&& this.hasMissingFileChefDeFile == other.hasMissingFileChefDeFile
				&& Objects.equals(this.missingFiles, other.missingFiles);
	}

	@Override
	public String toString() {
		return "DL1DirectionRegionaleTablesFiles [code=" + this.code + ", count=" + this.count + ", missingFiles="
				+ this.missingFiles + ", hasMissingFile=" + this.hasMissingFile + ", hasMissingFileChefDeFile="
				+ this.hasMissingFileChefDeFile + "]";
	}

}
