package fr.polemploi.suivi.migration.entities.dl1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.polemploi.suivi.migration.entities.DatedInfos;

/**
 * POJO contenant la liste des fichier DL1 par direction.
 *
 * @author jbourrea
 *
 */
public class DL1DirectionRegionaleTablesFilesContainer extends DatedInfos {

	private List<DL1DirectionRegionaleTablesFiles> dl1Files;

	private boolean hasMissingFile = false;

	public DL1DirectionRegionaleTablesFilesContainer() {
		super();
		this.dl1Files = new ArrayList<>();
	}

	public List<DL1DirectionRegionaleTablesFiles> getDl1Files() {
		return this.dl1Files;
	}

	public void addDl1Files(DL1DirectionRegionaleTablesFiles dl1Files) {
		this.dl1Files.add(dl1Files);
	}

	public boolean getHasMissingFile() {
		return this.hasMissingFile;
	}

	public void setHasMissingFile(boolean hasMissingFile) {
		this.hasMissingFile = hasMissingFile;
	}

	/**
	 * Donne le total des fichiers trouvé pour toutes les DR.
	 *
	 * @return
	 */
	public Integer getTotal() {

		Integer total = 0;

		for (DL1DirectionRegionaleTablesFiles drFiles : this.dl1Files) {
			total = total + drFiles.getCount();
		}
		return total;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.dl1Files, this.hasMissingFile);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof DL1DirectionRegionaleTablesFilesContainer other)) {
			return false;
		}
		return Objects.equals(this.dl1Files, other.dl1Files) && this.hasMissingFile == other.hasMissingFile;
	}

	@Override
	public String toString() {
		return "DL1DirectionRegionaleTablesFilesContainer [dl1Files=" + this.dl1Files + ", hasMissingFile="
				+ this.hasMissingFile + "]";
	}

}
