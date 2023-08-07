package fr.polemploi.suivi.migration.api.path;

/**
 * Interface de distribution des chemins sur le FS.
 *
 * @author jbourrea
 *
 */
public interface PathDispenser {

	/**
	 * Retourne le chemin vers le dossier root.
	 *
	 * @return {@link String}
	 */
	public String getRootFolder();

	/**
	 * Retourne le chemin vers le dossier des fichier de DL1
	 *
	 * @return {@link String}
	 */
	public String getOutilChargementRootFolder();

	/**
	 * Retourne le chemin vers le dossier des fichier de DL1 sur le NAS.
	 *
	 * @return {@link String}
	 */
	public String getDL1NasFolder();

	/**
	 * Retourne le chemin vers le dossier des fichier de DB2
	 *
	 * @return {@link String}
	 */
	public String getDB2RootFolder();

	/**
	 * Retourne le chemin vers le fichier de log général de la conversion de DB2.
	 *
	 * @return {@link String}
	 */
	public String getDB2ConversionAllFile();

	/**
	 * Retourne le chemin vers le fichier de log général du chargement de DB2.
	 *
	 * @return {@link String}
	 */
	public String getDB2ChargementAllFile();

	/**
	 * Retourne le chemin vers le dossier root.
	 *
	 * @return {@link String}
	 */
	public String getDL1SchemaFolder();

	/**
	 * Retourne le chemin vers le dossier root.
	 *
	 * @return {@link String}
	 */
	public String getDL1VolumetrieOracle();

	/**
	 * Retourne le chemin vers le dossier des logs de chargement DB2.
	 *
	 * @return {@link String}
	 */
	public String getDB2LoadOracle();

	/**
	 * Retourne le chemin vers le fichier de version de l'outillage.
	 *
	 * @return {@link String}
	 */
	public String getVersionOutillage();

	/**
	 * Retourne le chemin vers le dossier des logs de conversion DB2.
	 *
	 * @return {@link String}
	 */
	public String getDB2ConvertOracle();
}
