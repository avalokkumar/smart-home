package com.clay.sh.smartlighting;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmartLightingApplication implements CommandLineRunner {

	public static void main(String[] args) {
		System.out.println("SmartLightingApplication Running...");
		SpringApplication.run(SmartLightingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("SmartLightingApplication started...");
		// Add your post-startup code here
	}
}
