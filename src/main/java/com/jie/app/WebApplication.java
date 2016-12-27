package com.jie.app;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.jie.game.BoardType;
import com.jie.game.ConsoleGame;
import com.jie.game.GameBoard;

@SpringBootApplication
@Configuration
@ComponentScan({ "com.jie.app", "com.jie.controller.*" })
public class WebApplication {

	@Bean
	public ConsoleGame game() {
		return new ConsoleGame();
	}

	@Bean
	public GameBoard gameBoard() {
		return new GameBoard();
	}

	// @Bean
	// public Scanner scanner() {
	// return new Scanner(System.in);
	// }

	// @Bean
	// public WebMvcConfigurerAdapter forwardToIndex() {
	// return new WebMvcConfigurerAdapter() {
	// @Override
	// public void addViewControllers(ViewControllerRegistry registry) {
	// // forward requests to /admin and /user to their index.html
	// registry.addViewController("/admin").setViewName(
	// "forward:/admin/index.html");
	// registry.addViewController("/user").setViewName(
	// "forward:/user/index.html");
	// }
	// };
	// }

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
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
