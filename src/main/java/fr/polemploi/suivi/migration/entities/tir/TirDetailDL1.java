package fr.polemploi.suivi.migration.entities.tir;

import java.util.Objects;

import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesDetailsContainer;
import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesFilesContainer;
import fr.polemploi.suivi.migration.entities.dl1.DL1DirectionRegionaleTablesVolumeContainer;

/**
 * POJO représentant un tir.
 *
 * TODO JBOU spécialisation DL1 et DB2.
 *
 * @author jbourrea
 *
 */
public class TirDetailDL1 extends Tir {

	private DL1DirectionRegionaleTablesFilesContainer dl1ControleFilesContainer;

	private DL1DirectionRegionaleTablesFilesContainer dl1ControleLogsContainer;

	private DL1DirectionRegionaleTablesDetailsContainer dl1TablesDetailsContainer;

	private DL1DirectionRegionaleTablesVolumeContainer dL1TablesVolumeContainer;

	public TirDetailDL1() {
		super();
	}

	public DL1DirectionRegionaleTablesFilesContainer getDl1ControleFilesContainer() {
		return this.dl1ControleFilesContainer;
	}

	public void setDl1ControleFilesContainer(DL1DirectionRegionaleTablesFilesContainer dl1ControleFilesContainer) {
		this.dl1ControleFilesContainer = dl1ControleFilesContainer;
	}

	public DL1DirectionRegionaleTablesFilesContainer getDl1ControleLogsContainer() {
		return this.dl1ControleLogsContainer;
	}

	public void setDl1ControleLogsContainer(DL1DirectionRegionaleTablesFilesContainer dl1ControleLogsContainer) {
		this.dl1ControleLogsContainer = dl1ControleLogsContainer;
	}

	public DL1DirectionRegionaleTablesDetailsContainer getDl1TablesDetailsContainer() {
		return this.dl1TablesDetailsContainer;
	}

	public void setDL1TablesDetailsContainer(DL1DirectionRegionaleTablesDetailsContainer dL1TablesDetailsContainer) {
		this.dl1TablesDetailsContainer = dL1TablesDetailsContainer;
	}

	public DL1DirectionRegionaleTablesVolumeContainer getdL1TablesVolumeContainer() {
		return this.dL1TablesVolumeContainer;
	}

	public void setdL1TablesVolumeContainer(DL1DirectionRegionaleTablesVolumeContainer dL1TablesVolumeContainer) {
		this.dL1TablesVolumeContainer = dL1TablesVolumeContainer;
	}

	public void setDl1TablesDetailsContainer(DL1DirectionRegionaleTablesDetailsContainer dl1TablesDetailsContainer) {
		this.dl1TablesDetailsContainer = dl1TablesDetailsContainer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(this.dL1TablesVolumeContainer, this.dl1TablesDetailsContainer,
				this.dl1ControleFilesContainer, this.dl1ControleLogsContainer);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || !(obj instanceof TirDetailDL1 other)) {
			return false;
		}
		return Objects.equals(this.dL1TablesVolumeContainer, other.dL1TablesVolumeContainer)
				&& Objects.equals(this.dl1TablesDetailsContainer, other.dl1TablesDetailsContainer)
				&& Objects.equals(this.dl1ControleFilesContainer, other.dl1ControleFilesContainer)
				&& Objects.equals(this.dl1ControleLogsContainer, other.dl1ControleLogsContainer);
	}

	@Override
	public String toString() {
		return "TirDetailDL1 [dl1ControleFilesContainer=" + this.dl1ControleFilesContainer
				+ ", dl1ControleLogsContainer=" + this.dl1ControleLogsContainer + ", Dl1TablesDetailsContainer="
				+ this.dl1TablesDetailsContainer + ", DL1TablesVolumeContainer=" + this.dL1TablesVolumeContainer + "]";
	}

}
