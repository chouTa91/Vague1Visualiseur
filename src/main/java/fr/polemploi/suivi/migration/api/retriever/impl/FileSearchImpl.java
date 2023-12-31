package fr.polemploi.suivi.migration.api.retriever.impl;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import fr.polemploi.suivi.migration.api.retriever.FileSearch;

@Component
public class FileSearchImpl implements FileSearch {

	@Override
	public List<String> searchWithWc(Path rootDir, String pattern) throws IOException {
		List<String> matchesList = new ArrayList<String>();

		FileVisitor<Path> matcherVisitor = new SimpleFileVisitor<Path>() {

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attribs) throws IOException {
				FileSystem fs = FileSystems.getDefault();
				PathMatcher matcher = fs.getPathMatcher(pattern);
				Path name = file.getFileName();
				if (matcher.matches(name)) {
					matchesList.add(name.toString());
				}
				return FileVisitResult.CONTINUE;
			}
		};

		Files.walkFileTree(rootDir, matcherVisitor);

		return matchesList;
	}
}
