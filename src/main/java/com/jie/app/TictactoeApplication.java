package com.jie.app;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan({ "com.jie.app", "com.jie.controller" })
public class TictactoeApplication {

	// @Bean
	// public Game game() {
	// return new Game();
	// }
	//
	// @Bean
	// public GameBoard gameBoard() {
	// return new GameBoard();
	// }
	//
	// @Bean
	// public Scanner scanner() {
	// return new Scanner(System.in);
	// }

	public static void main(String[] args) {
		SpringApplication.run(TictactoeApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out
					.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
			System.out.println(this.getClass().getCanonicalName());
		};
	}
}
