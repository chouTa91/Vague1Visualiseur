package fr.polemploi.suivi.migration.entities.message.logs;

import java.util.Objects;

import fr.polemploi.suivi.migration.entities.message.Message;

public class RawFile extends Message<RawFile> {

	private String content;

	public RawFile() {
		super();
	}

	public RawFile(String fileName, String content) {
		super();
		this.fileName = fileName;
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(this.content);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj) || !(obj instanceof RawFile other)) {
			return false;
		}
		return Objects.equals(this.content, other.content);
	}

	@Override
	public String toString() {
		return "RawFile [content=" + this.content + "]";
	}

}
