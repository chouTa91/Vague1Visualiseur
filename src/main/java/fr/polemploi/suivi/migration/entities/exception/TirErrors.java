package fr.polemploi.suivi.migration.entities.exception;

import java.util.Objects;

import fr.polemploi.suivi.migration.entities.enums.error.ErrorsEnum;

/**
 * Exception d'un tir, utilisé pour recenser les erreurs potentielles et avérées
 * sur un tir.
 *
 * @author jbourrea
 *
 */
public class TirErrors {

	private String message;

	private String code;

	public TirErrors(String message) {
		this.message = message;
	}

	/**
	 * Constructeur de {@link TirErrors}.
	 *
	 * @param error
	 *               {@link ErrorsEnum} L'enum de l'erreur.
	 * @param params
	 *               {@link String}... La liste des paramètres du message.
	 */
	public TirErrors(ErrorsEnum error, String... params) {
		this.message = String.format(error.getMessage(), (Object[]) params);
		this.code = error.getCode();
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(this.code, this.message);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TirErrors)) {
			return false;
		}
		TirErrors other = (TirErrors) obj;
		return Objects.equals(this.code, other.code) && Objects.equals(this.message, other.message);
	}

	@Override
	public String toString() {
		return "TirException [message=" + this.message + ", code=" + this.code + "]";
	}

}
