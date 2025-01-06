package com.aluracursos.gutendex;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aluracursos.gutendex.main.Main;

@SpringBootApplication
public class GutendexApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GutendexApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello world from Spring Boot");

		Main main = new Main();
		main.bookChallenge();
	}
}
