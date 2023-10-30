package com.attrecto.academy.java.courseapp.validator;

import com.attrecto.academy.java.courseapp.model.dto.CreateCourseDto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, CreateCourseDto> {
    public void initialize(ValidDateRange constraint) {}

    public boolean isValid(CreateCourseDto createCourseDto, ConstraintValidatorContext context) {
    	return createCourseDto.getFromDate().isBefore(createCourseDto.getUntilDate());
    }
}
