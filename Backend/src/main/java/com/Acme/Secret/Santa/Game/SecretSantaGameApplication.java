package com.Acme.Secret.Santa.Game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecretSantaGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecretSantaGameApplication.class, args);
		System.out.println("Started...");
	}

}
