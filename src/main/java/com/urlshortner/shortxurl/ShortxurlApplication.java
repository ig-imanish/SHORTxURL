package com.urlshortner.shortxurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.github.cdimascio.dotenv.Dotenv;

@EnableScheduling
@SpringBootApplication
public class ShortxurlApplication {

	public static void main(String[] args) {

		try {
            Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
            dotenv.entries().forEach(e -> {
                System.setProperty(e.getKey(), e.getValue());
                System.out.println("Loaded env var: " + e.getKey());
            });
        } catch (Exception e) {
            System.err.println("Warning: Failed to load .env file. " + e.getMessage());
        }

		SpringApplication.run(ShortxurlApplication.class, args);
	}

}
