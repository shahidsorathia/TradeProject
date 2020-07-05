package com.upstox.TradeProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class TradeProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeProjectApplication.class, args);

		File file = new File("Test.txt");
	}

}
