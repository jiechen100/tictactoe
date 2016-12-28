package com.jie.app;

import java.util.Arrays;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.jie.game.ConsoleGame;
import com.jie.game.GameBoard;

@Configuration
@PropertySource("classpath:application.properties")
public class ConsoleApplication {

	@Bean
	public ConsoleGame consoleGame() {
		System.out.println(this.getClass().getCanonicalName()
				+ "::consoleGame()");
		return new ConsoleGame();
	}

	@Bean
	public GameBoard gameBoard() {
		System.out
				.println(this.getClass().getCanonicalName() + "::gameBoard()");
		return new GameBoard();
	}

	@Bean
	public Scanner scanner() {
		return new Scanner(System.in);
	}

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				ConsoleApplication.class);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
			System.out.println(this.getClass().getCanonicalName());
		};
	}
}
