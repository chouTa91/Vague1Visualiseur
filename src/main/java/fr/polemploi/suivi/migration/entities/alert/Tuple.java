package fr.polemploi.suivi.migration.entities.alert;

import java.util.Objects;

/**
 * Implémentation d'un Tuple.<br>
 *
 * <b>Immutable</b>
 *
 * @author jbourrea
 *
 * @param <F>
 * @param <S>
 */
public final class Tuple<F, S> {
	final public F first;
	final public S second;

	public Tuple(F first, S second) {
		this.first = first;
		this.second = second;
	}

	public F getFirst() {
		return this.first;
	}

	public S getSecond() {
		return this.second;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.first, this.second);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof @SuppressWarnings("rawtypes") Tuple other)) {
			return false;
		}
		return Objects.equals(this.first, other.first) && Objects.equals(this.second, other.second);
	}

	@Override
	public String toString() {
		return "Tuple [first=" + this.first + ", second=" + this.second + "]";
	}

}
