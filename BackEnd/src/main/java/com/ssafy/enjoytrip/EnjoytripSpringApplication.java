package com.ssafy.enjoytrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ssafy"})
public class EnjoytripSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnjoytripSpringApplication.class, args);
	}

}
