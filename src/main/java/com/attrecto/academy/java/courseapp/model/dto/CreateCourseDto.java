package com.attrecto.academy.java.courseapp.model.dto;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import com.attrecto.academy.java.courseapp.validator.ValidDateRange;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema
@ValidDateRange
public class CreateCourseDto {
	@Size(min = 10, max = 100)
	@NotBlank
	@Schema(description = "Title of the course", example = "Java course")
	private String title;
	
	@Size(min = 10, max = 1000)
	@NotBlank
	@Schema(description = "Description of the course", example = "Java fundamentals and Spring Boot")	
	private String description;
	
	@NotBlank
	@Schema(description = "URL for the course", example = "https://attrecto.com/academy/course/java")	
	private String url;
	
	@NotNull
	@Schema(description = "Id of the of the course author", example = "1")	
	private Integer authorId;
	
	@NotNull
	@Schema(description = "Start date of the course", example = "2023-10-25")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fromDate;
	
	@NotNull
	@Schema(description = "End date of the course", example = "2023-11-25")		
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate untilDate;
	
	private Set<Integer> studentIds = new HashSet<>();
	
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
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
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
	public Set<Integer> getStudentIds() {
		return studentIds;
	}
	public void setStudentIds(Set<Integer> studentIds) {
		this.studentIds = studentIds;
	}
}
