package fr.polemploi.suivi.migration.api.retriever;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileSearch {

	/**
	 * Recherche la liste des fichiers avec wildcards.
	 *
	 * @param rootDir
	 * @param pattern
	 * @return
	 * @throws IOException
	 */
	List<String> searchWithWc(Path rootDir, String pattern) throws IOException;

}
