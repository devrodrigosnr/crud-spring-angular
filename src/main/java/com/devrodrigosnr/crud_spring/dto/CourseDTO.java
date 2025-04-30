package com.devrodrigosnr.crud_spring.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CourseDTO(
    Long id,
    @NotBlank @NotNull String name,
    @NotBlank @NotNull @Pattern(regexp = "Backend|Frontend") String category,
    List<LessonDTO> lessons) {
}
