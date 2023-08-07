package fr.polemploi.suivi.migration.monitor.event;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.StringJoiner;

public class Event {

	private final String action;
	private final String path;

	public Event(WatchEvent<Path> event, Path path) {
		this.action = event.kind().toString();
		this.path = path.toString();
	}

	public String getAction() {
		return this.action;
	}

	public String getPath() {
		return this.path;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Event.class.getSimpleName() + "[", "]").add("action='" + this.action + "'")
				.add("path='" + this.path + "'").toString();
	}

}
