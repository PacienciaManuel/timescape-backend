package com.timescape.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	public FileSystemStorageService(@Value("${storage.location}") String storageLocation) {
		this.rootLocation = Paths.get(storageLocation).toAbsolutePath();
	}

	@Override
	public void init() {
		try {
			if (!Files.exists(rootLocation)) {				
				Files.createDirectory(rootLocation);
			}
		} catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	@Override
	public String store(MultipartFile file) {
		try {
			String originalFilename = file.getOriginalFilename();
			if (file.isEmpty() || originalFilename == null) {
				throw new StorageException("Failed to store empty file: " + originalFilename);
			}
			String filename = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));
			file.transferTo(rootLocation.resolve(filename));
			return filename;
		} catch (IOException e) {
			throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
		}
	}

	@Override
	public void delete(String ... files) {
		for (String filename : files) {
			try {
				Files.deleteIfExists(rootLocation.resolve(filename));
			} catch (IOException e) {
				throw new StorageDeleteException("Failed to delete file: " + filename, e);
			}
		}
	}
}
