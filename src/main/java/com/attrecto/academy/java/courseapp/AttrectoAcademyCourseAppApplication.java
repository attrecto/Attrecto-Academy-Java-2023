package com.attrecto.academy.java.courseapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@OpenAPIDefinition
public class AttrectoAcademyCourseAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(AttrectoAcademyCourseAppApplication.class, args);
	}

}
