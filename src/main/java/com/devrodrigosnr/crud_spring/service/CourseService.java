package com.devrodrigosnr.crud_spring.service;

import com.devrodrigosnr.crud_spring.model.Course;
import com.devrodrigosnr.crud_spring.repository.CourseRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private CourseRepository repository;

    public CourseService(CourseRepository repository){
        this.repository = repository;
    }

    public List<Course> buscarTodosOsCursos(){
        return this.repository.findAll();
    }
}
