package com.timescape.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import lombok.extern.java.Log;

@Log
public class TestMIME {

	public static void main(String[] args) throws IOException {
		Path path = new File("product.ogg").toPath();
	    String mimeType = Files.probeContentType(path);
	    log.info("mimeType: " + mimeType);
	}

}
