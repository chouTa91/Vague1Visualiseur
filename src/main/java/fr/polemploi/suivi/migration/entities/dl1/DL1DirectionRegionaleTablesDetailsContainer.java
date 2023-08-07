package fr.polemploi.suivi.migration.entities.dl1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.polemploi.suivi.migration.entities.DatedInfos;

/**
 * POJO contenant le détail de DL1 par direction.
 *
 * @author jbourrea
 *
 */
public class DL1DirectionRegionaleTablesDetailsContainer extends DatedInfos {

	List<DL1DirectionRegionaleTablesDetails> dl1Details;

	// TODO JBOU total des resultats

	public DL1DirectionRegionaleTablesDetailsContainer() {
		super();
		this.dl1Details = new ArrayList<>();
	}

	public List<DL1DirectionRegionaleTablesDetails> getDl1Details() {
		return this.dl1Details;
	}

	public void addDl1Details(DL1DirectionRegionaleTablesDetails dl1Detail) {
		this.dl1Details.add(dl1Detail);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.dl1Details);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof DL1DirectionRegionaleTablesDetailsContainer other)) {
			return false;
		}
		return Objects.equals(this.dl1Details, other.dl1Details);
	}

	@Override
	public String toString() {
		return "DL1DirectionRegionaleTablesDetailsContainer [dl1Details=" + this.dl1Details + "]";
	}

}
