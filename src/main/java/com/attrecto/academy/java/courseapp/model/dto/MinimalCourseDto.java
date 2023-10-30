package com.attrecto.academy.java.courseapp.model.dto;


import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class MinimalCourseDto {
	@NotBlank
	@Schema(description = "Id of the course", example = "1")
	private int id;
	@NotBlank
	@Schema(description = "Title of the course", example = "Spring boot")
	private String title;
	@NotBlank
	@Schema(description = "Description of the course", example = "Java fundamentals and Spring Boot")
	private String description;
	@NotBlank
	@Schema(description = "URL for the course", example = "https://attrecto.com/academy/course/java")	
	private String url;
	@NotBlank
	@Schema(description = "Beginning of the course", example = "2023-10-25")		
	private LocalDate fromDate;
	@NotBlank
	@Schema(description = "End of the course", example = "2023-11-25")		
	private LocalDate untilDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getUntilDate() {
		return untilDate;
	}
	public void setUntilDate(LocalDate untilDate) {
		this.untilDate = untilDate;
	}
}
