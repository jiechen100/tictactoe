package com.jie;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class TictactoeApplication {

	@Bean
	public Game game() {
		return new Game();
	}

	@Bean
	public GameBoard gameBoard() {
		return new GameBoard();
	}

	@Bean
	public Scanner scanner() {
		return new Scanner(System.in);
	}

	public static void main(String[] args) {
		SpringApplication.run(TictactoeApplication.class, args);
	}
}
