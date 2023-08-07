package fr.polemploi.suivi.migration.api.retriever;

import java.io.IOException;
import java.nio.file.Path;

/**
 * API de recupération de fichiers.
 *
 * @author jbourrea
 *
 */
public interface FilesRetrieverApi {

	/**
	 * Retourne le fichier demandé depuis le FS.
	 *
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	String retrieveFile(Path filePath) throws IOException;

}
