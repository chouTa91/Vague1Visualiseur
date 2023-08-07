package fr.polemploi.suivi.migration.entities.message;

import java.util.Objects;

/**
 * Abstraction d'un message.
 */
public abstract class Message<T> {

	protected String fileName;

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.fileName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Message<?> other)) {
			return false;
		}
		return Objects.equals(this.fileName, other.fileName);
	}

	@Override
	public String toString() {
		return "Message [fileName=" + this.fileName + "]";
	}

}
