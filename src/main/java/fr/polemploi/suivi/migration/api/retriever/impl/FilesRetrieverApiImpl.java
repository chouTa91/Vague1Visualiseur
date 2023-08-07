package fr.polemploi.suivi.migration.api.retriever.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import org.springframework.stereotype.Component;

import fr.polemploi.suivi.migration.api.retriever.FilesRetrieverApi;

/**
 * Implémentation de {@link FilesRetrieverApi}.
 *
 * @author jbourrea
 *
 */
@Component
public class FilesRetrieverApiImpl implements FilesRetrieverApi {

	@Override
	public String retrieveFile(Path filePath) throws IOException {
		Objects.requireNonNull(filePath);

		return Files.readString(filePath, Charset.forName("ISO-8859-1"));
	}

}
