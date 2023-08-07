package fr.polemploi.suivi.migration.entities.tir;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * POJO repésentant la synthèse d'un tir.
 *
 * @author jbourrea
 *
 */
public class TirSynthesis extends Tir {

	private List<DRSynthesis> directionSynthesis;

	public TirSynthesis() {
		super();
		this.directionSynthesis = new ArrayList<>();
	}

	/**
	 * @return the directionSynthesis
	 */
	public List<DRSynthesis> getDirectionSynthesis() {
		return this.directionSynthesis;
	}

	/**
	 * @param directionSynthesis
	 *                           the directionSynthesis add
	 */
	public void addAllDirectionSynthesis(List<DRSynthesis> directionSynthesis) {
		this.directionSynthesis.addAll(directionSynthesis);
	}

	/**
	 * @param directionSynthesis
	 *                           the directionSynthesis add
	 */
	public void addDirectionSynthesis(DRSynthesis directionSynthesis) {
		this.directionSynthesis.add(directionSynthesis);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(this.directionSynthesis);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || !(obj instanceof TirSynthesis)) {
			return false;
		}
		TirSynthesis other = (TirSynthesis) obj;
		return Objects.equals(this.directionSynthesis, other.directionSynthesis);
	}

	@Override
	public String toString() {
		return "TirSynthesis [directionSynthesis=" + this.directionSynthesis + ", getId()=" + this.getId()
				+ ", getErrors()=" + this.getErrors() + "]";
	}

}
