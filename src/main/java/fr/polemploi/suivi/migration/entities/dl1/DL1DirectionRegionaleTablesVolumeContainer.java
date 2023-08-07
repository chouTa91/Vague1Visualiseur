package fr.polemploi.suivi.migration.entities.dl1;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import fr.polemploi.suivi.migration.entities.DatedInfos;

/**
 * POJO container de la volumétrie des tables dans Oracle.
 *
 * @author jbourrea
 *
 */
public class DL1DirectionRegionaleTablesVolumeContainer extends DatedInfos {

	/**
	 * <b>Key</b> : code table<br>
	 * <b>Value</b> : volumétrie
	 */
	Map<String, Dl1Volumes> volume;

	public DL1DirectionRegionaleTablesVolumeContainer() {
		this.volume = new HashMap<>();
	}

	public Map<String, Dl1Volumes> getVolume() {
		return this.volume;
	}

	public void putVolume(String codeTable, Dl1Volumes volume) {
		this.volume.put(codeTable, volume);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.volume);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof DL1DirectionRegionaleTablesVolumeContainer other)) {
			return false;
		}
		return Objects.equals(this.volume, other.volume);
	}

	@Override
	public String toString() {
		return "DL1DirectionRegionaleTablesVolumeContainer [volume=" + this.volume + "]";
	}

}
