package com.urlshortner.shortxurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ShortxurlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortxurlApplication.class, args);
	}

}
