package fr.polemploi.suivi.migration.entities.tir;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import fr.polemploi.suivi.migration.entities.exception.TirErrors;

/**
 * Abstraction d'un tir de migration.
 *
 * @author jbourrea
 *
 */
public abstract class Tir {

	private Integer id = 1; // TODO JBOU calcul de l'id du tir ? Passer par une date pour en déduire le tir
							// concerné ? par un dossier de travail différent ?

	private List<TirErrors> errors;

	public Tir() {
		this.errors = new ArrayList<>();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<TirErrors> getErrors() {
		return this.errors;
	}

	public void addAllErrors(List<TirErrors> errors) {
		this.errors.addAll(errors);
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.errors, this.id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Tir)) {
			return false;
		}
		Tir other = (Tir) obj;
		return Objects.equals(this.errors, other.errors) && Objects.equals(this.id, other.id);
	}

	@Override
	public String toString() {
		return "Tir [id=" + this.id + ", errors=" + this.errors + "]";
	}

}
