package no.haakon.flatsum.auxilliary.resource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.stream.Stream;

public class ResourceConfigurationParser {

	File config;
	
	public ResourceConfigurationParser(File resourceConfig) {
		this.config = resourceConfig;
	}
	
	public Stream<String[]> parse() throws IOException {
		return Files.lines(config.toPath(), StandardCharsets.UTF_8).map((String line) -> line.split("\\s+"));
	}

}
