package fr.polemploi.suivi.migration.monitor.event;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

import org.springframework.context.ApplicationEvent;

/**
 * Evenement du reactor pour les changements de fichiers et dossier.
 *
 * @author jbourrea
 *
 */
public class FolderChangeEvent extends ApplicationEvent {

	private static final long serialVersionUID = -1257397999014702415L;

	private final Event event;

	public FolderChangeEvent(Object source, WatchEvent<Path> pathEvent, Path path) {
		super(source);
		this.event = new Event(pathEvent, path);
	}

	public Event getEvent() {
		return this.event;
	}

}