package com.devrodrigosnr.crud_spring.service;

import com.devrodrigosnr.crud_spring.model.Course;
import com.devrodrigosnr.crud_spring.repository.CourseRepository;

import jakarta.validation.Valid;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private CourseRepository repository;

    public CourseService(CourseRepository repository){
        this.repository = repository;
    }

    public List<Course> list(){
        return this.repository.findAll();
    }

    public Course create(@Valid Course course){
        return repository.save(course);
    }

    public Optional<Course> findById(Long id) {
        return repository.findById(id);
	}

    public Optional<Course> update(Long id, @Valid Course course) {
        return repository.findById(id)
                .map(recordFound -> {
                    recordFound.setName(course.getName());
                    recordFound.setCategory(course.getCategory());
                    return repository.save(recordFound);
                });
    }

    public boolean delete(Long id) {
    return repository.findById(id)
            .map(recordFound -> {
                repository.deleteById(id);
                return true;
            })
            .orElse(false);
    }
}
