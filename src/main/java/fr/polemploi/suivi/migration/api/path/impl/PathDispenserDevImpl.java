package fr.polemploi.suivi.migration.api.path.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import fr.polemploi.suivi.migration.api.path.PathDispenser;

/**
 * Dispenser des chemins sur le FS pour la platefrome de <b>DEV</b>
 *
 * @author jbourrea
 *
 */
@Component
@Profile("22M04")
public class PathDispenserDevImpl implements PathDispenser {

	@Value("${pe.suivi.log.root.folder}")
	private String pathToRootFolder;

	@Override
	public String getRootFolder() {
		return this.pathToRootFolder;
	}

	@Override
	public String getOutilChargementRootFolder() {
		return this.pathToRootFolder + "\\fichierbouchon\\OUTIL_CHARGEMENT_IMS\\outil_V2\\DL1\\";
	}

	@Override
	public String getDL1NasFolder() {
		return this.pathToRootFolder + "\\fichierbouchon\\COPY_FROM_NAS\\DL1\\";
	}

	@Override
	public String getDB2RootFolder() {
		return this.pathToRootFolder + "\\fichierbouchon\\COPY_FROM_NAS\\DB2\\";
	}

	@Override
	public String getDB2ConversionAllFile() {
		return this.pathToRootFolder + "\\fichierbouchon\\COPY_FROM_NAS\\conversion-all.log";
	}

	@Override
	public String getDB2ChargementAllFile() {
		return this.pathToRootFolder + "\\fichierbouchon\\COPY_FROM_NAS\\rechargement-all.log";
	}

	@Override
	public String getDL1SchemaFolder() {
		return this.pathToRootFolder + "\\fichierbouchon\\OUTIL_CHARGEMENT_IMS\\outil_V2\\DL1\\DMT\\DL1\\REL\\";
	}

	@Override
	public String getVersionOutillage() {
		return this.pathToRootFolder + "\\fichierbouchon\\OUTIL_CHARGEMENT_IMS\\outil_V2\\DL1\\DMT\\DL1\\version";
	}

	@Override
	public String getDL1VolumetrieOracle() {
		return this.pathToRootFolder
				+ "\\fichierbouchon\\OUTIL_CHARGEMENT_IMS\\outil_V2\\DL1\\DMT\\DL1\\REL\\resultat_volumetrie_DL1";
	}

	@Override
	public String getDB2ConvertOracle() {
		return this.pathToRootFolder
				+ "\\fichierbouchon\\OUTIL_CHARGEMENT_IMS\\outil_V3\\DB2\\DMT\\DB2\\common\\convert\\log\\";
	}

	@Override
	public String getDB2LoadOracle() {
		return this.pathToRootFolder
				+ "\\fichierbouchon\\OUTIL_CHARGEMENT_IMS\\outil_V3\\DB2\\DMT\\DB2\\common\\load\\log\\";
	}
}
