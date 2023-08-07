package fr.polemploi.suivi.migration.entities.enums.error;

/**
 * Enumération des erreurs fonctionnelles de l'application.
 *
 * @author jbourrea
 *
 */
public enum ErrorsEnum {

	DL1_CONTROLE_FICHIERS("Nombre de fichier incorrect pour %s", "dl1-controle-fichier"),
	DB2_CONTROLE_FICHIERS("Nombre de fichier incorrect", "dl2-controle-fichier"),
	DB2_CHARGEMENT("Erreur lors du chargement de DB2", "db2-chargement"),
	DB2_CONVERSION("Erreur lors de la conversion de DB2", "db2-conversion");

	ErrorsEnum(String message, String code) {
		this.message = message;
		this.code = code;
	}

	private String message;

	private String code;

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
