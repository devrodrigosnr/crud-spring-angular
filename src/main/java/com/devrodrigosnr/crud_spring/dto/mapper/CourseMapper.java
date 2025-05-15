package com.devrodrigosnr.crud_spring.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.devrodrigosnr.crud_spring.dto.CourseDTO;
import com.devrodrigosnr.crud_spring.dto.LessonDTO;
import com.devrodrigosnr.crud_spring.enums.Category;
import com.devrodrigosnr.crud_spring.model.Course;
import com.devrodrigosnr.crud_spring.model.Lesson;

@Component
public class CourseMapper {

    public CourseDTO toDTO(Course course) {
        if(course == null){
            return null;
        }
        List<LessonDTO> lessonsDTO = course.getLessons()
                .stream()
                .map(lesson -> new LessonDTO(lesson.getId(), lesson.getName(), lesson.getYoutubeURL())).collect(Collectors.toList());
        return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue(), lessonsDTO);
    }

    public Course toEntity(CourseDTO courseDTO) {
        if(courseDTO == null) {
            return null;
        }
        Course course = new Course();
        if(courseDTO.id() != null) {
            course.setId(courseDTO.id());
        }
        course.setName(courseDTO.name());
        course.setCategory(convertToCategory(courseDTO.category()));

        List<Lesson> lessons = courseDTO.lessons().stream().map(lessonDTO -> {
            var lesson = new Lesson();
            lesson.setId(lessonDTO.id());
            lesson.setName(lessonDTO.name());
            lesson.setYoutubeURL(lessonDTO.youtubeURL());
            lesson.setCourse(course);
            return lesson;
        }).collect(Collectors.toList());
        course.setLessons(lessons);
        return course;
    }

    public Category convertToCategory(String value) {
        if(value == null) {
            return null;
        }

        return switch (value) {
            case "Backend" -> Category.BACKEND;
            case "Frontend" -> Category.FRONTEND;
            default -> throw new IllegalArgumentException("Invalid category value: " + value);
        };
    }
}
