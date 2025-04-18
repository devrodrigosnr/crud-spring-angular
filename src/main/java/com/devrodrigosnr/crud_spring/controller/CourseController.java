package com.devrodrigosnr.crud_spring.controller;

import com.devrodrigosnr.crud_spring.model.Course;
import com.devrodrigosnr.crud_spring.service.CourseService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> list(){
        return courseService.list();
    }

    @PostMapping
    public ResponseEntity<Course> create(@RequestBody @Valid Course course){
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.create(course));
    }

    @GetMapping("/{id}")
	public ResponseEntity<Course> findById(@PathVariable Long id) {
		return courseService.findById(id)
                .map(course -> ResponseEntity.ok(course))
                .orElse(ResponseEntity.notFound().build());

	}

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable Long id, 
        @RequestBody @Valid Course course) {
        return courseService.update(id, course)
        .map(recordFound -> ResponseEntity.ok().body(recordFound))
        .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(courseService.delete(id)){
            return ResponseEntity.noContent().<Void>build();
        }
        return ResponseEntity.notFound().build();
    }
}
