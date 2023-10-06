package com.attrecto.academy.java.courseapp.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MinimalUserDto {
	@NotNull
	@Schema(description = "Id of the user", example = "1")	
	private int id;
	@NotBlank
	@Schema(description = "Name of the user", example = "user")	
	private String name;
	@NotBlank
	@Schema(description = "Email of the user", example = "user@gmail.com")
	private String email;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
