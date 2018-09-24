package com.tourbitz.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class TourBitzApplication {

	public static void main(String[] args) {
		SpringApplication.run(TourBitzApplication.class, args);
	}
}
