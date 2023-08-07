package fr.polemploi.suivi.migration.entities.tir;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Classe d'aggregat de tir.
 *
 * @author jbourrea
 *
 */
public class TirAggregate {

	private List<Tir> tirs;

	public TirAggregate() {
		this.tirs = new ArrayList<>();
	}

	public List<Tir> getTirs() {
		this.sortTir();
		return this.tirs;
	}

	public void addTir(Tir tir) {
		this.tirs.add(tir);
	}

	public void addAllTirs(List<Tir> tirs) {
		this.tirs.addAll(tirs);
	}

	/**
	 * Retourne le dernier tir de la liste.
	 *
	 * @return
	 */
	public Tir getLastTir() {
		this.sortTir();
		return !this.tirs.isEmpty() ? this.tirs.get(this.tirs.size() - 1) : null;
	}

	/**
	 * Tri de la liste des tirs de part leur id.
	 */
	private void sortTir() {
		Collections.sort(this.tirs, (Tir t1, Tir t2) -> t1.getId() - t2.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.tirs);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TirAggregate other)) {
			return false;
		}
		return Objects.equals(this.tirs, other.tirs);
	}

	@Override
	public String toString() {
		return "TirAggregate [tirs=" + this.tirs + "]";
	}

}
