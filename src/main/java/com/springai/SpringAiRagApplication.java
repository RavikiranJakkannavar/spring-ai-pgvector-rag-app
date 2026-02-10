package com.springai;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class SpringAiRagApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAiRagApplication.class, args);
	}

	@Bean
	CommandLineRunner checkDb(DataSource ds) {
		return args -> System.out.println("DB URL = " +
				ds.getConnection().getMetaData().getURL());
	}
}
