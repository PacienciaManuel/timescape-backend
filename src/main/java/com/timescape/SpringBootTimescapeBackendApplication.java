package com.timescape;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.timescape.storage.StorageService;

@EnableWebMvc
@SpringBootApplication
public class SpringBootTimescapeBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTimescapeBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return args -> storageService.init();
	}

}
