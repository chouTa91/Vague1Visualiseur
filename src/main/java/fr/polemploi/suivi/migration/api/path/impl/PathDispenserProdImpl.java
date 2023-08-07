package fr.polemploi.suivi.migration.api.path.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import fr.polemploi.suivi.migration.api.path.PathDispenser;

/**
 * Les chemins du FS de prod sont au format LINUX.
 *
 * @author jbourrea
 *
 */
@Component
@Profile("!22M04")
public class PathDispenserProdImpl implements PathDispenser {

	@Value("${pe.suivi.log.root.folder}")
	private String pathToRootFolder;

	@Override
	public String getRootFolder() {
		return this.pathToRootFolder;
	}

	@Override
	public String getOutilChargementRootFolder() {
		return this.pathToRootFolder + "/OUTIL_CHARGEMENT_IMS/outil_V2/DL1/";
	}

	@Override
	public String getDL1NasFolder() {
		return this.pathToRootFolder + "/COPY_FROM_NAS_V2/DL1/";
	}

	@Override
	public String getDB2RootFolder() {
		return this.pathToRootFolder + "/COPY_FROM_NAS_V2/DB2/";
	}

	@Override
	public String getDB2ConversionAllFile() {
		return this.pathToRootFolder + "/COPY_FROM_NAS_V2/conversion-all.log";
	}

	@Override
	public String getDB2ChargementAllFile() {
		return this.pathToRootFolder + "/COPY_FROM_NAS_V2/rechargement-all.log";
	}

	@Override
	public String getDL1SchemaFolder() {
		return this.pathToRootFolder + "/OUTIL_CHARGEMENT_IMS/outil_V2/DL1/DMT/DL1/REL/";
	}

	@Override
	public String getVersionOutillage() {
		return this.pathToRootFolder + "/OUTIL_CHARGEMENT_IMS/outil_V3/DL1/DMT/DL1/version";
	}

	@Override
	public String getDL1VolumetrieOracle() {
		return this.pathToRootFolder + "/OUTIL_CHARGEMENT_IMS/outil_V3/DL1/DMT/DL1/REL/resultat_volumetrie_DL1";
	}

	@Override
	public String getDB2LoadOracle() {
		return this.pathToRootFolder + "/OUTIL_CHARGEMENT_IMS/outil_V3/DB2/DMT/DB2/common/load/log/";
	}

	@Override
	public String getDB2ConvertOracle() {
		return this.pathToRootFolder + "/OUTIL_CHARGEMENT_IMS/outil_V3/DB2/DMT/DB2/common/convert/log/";
	}

}
