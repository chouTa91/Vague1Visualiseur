package fr.polemploi.suivi.migration.entities.tir;

import java.util.Objects;

import fr.polemploi.suivi.migration.entities.enums.common.DirectionRegionaleEnum;
import fr.polemploi.suivi.migration.entities.enums.common.StatusEnum;

/**
 * POJO de synthèse pour une DR.
 *
 * @author jbourrea
 *
 */
public class DRSynthesis {

	private DirectionRegionaleEnum direction;

	private StatusEnum status;

	public DirectionRegionaleEnum getDirection() {
		return this.direction;
	}

	public void setDirection(DirectionRegionaleEnum direction) {
		this.direction = direction;
	}

	public StatusEnum getStatus() {
		return this.status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.direction, this.status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof DRSynthesis other)) {
			return false;
		}
		return this.direction == other.direction && this.status == other.status;
	}

	@Override
	public String toString() {
		return "DRSynthesis [direction=" + this.direction + ", status=" + this.status + "]";
	}

}
