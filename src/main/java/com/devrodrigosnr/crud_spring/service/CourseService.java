package com.devrodrigosnr.crud_spring.service;

import com.devrodrigosnr.crud_spring.dto.CourseDTO;
import com.devrodrigosnr.crud_spring.dto.mapper.CourseMapper;
import com.devrodrigosnr.crud_spring.exception.RecordNotFoundException;
import com.devrodrigosnr.crud_spring.model.Course;
import com.devrodrigosnr.crud_spring.repository.CourseRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository repository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository repository, CourseMapper courseMapper){
        this.repository = repository;
        this.courseMapper = courseMapper;
    }

    public List<CourseDTO> list(){
        return repository.findAll()
            .stream()
            .map(courseMapper::toDTO)
            .collect(Collectors.toList());
    }

    public CourseDTO create(@Valid @NotNull CourseDTO course){
        return courseMapper.toDTO(repository.save(courseMapper.toEntity(course)));
    }

    public CourseDTO findById(Long id) {
        return repository.findById(id).map(courseMapper::toDTO)
            .orElseThrow(() -> new RecordNotFoundException(id));
	}

    public CourseDTO update(Long id, @Valid CourseDTO courseDTO) {
        return repository.findById(id)
                .map(recordFound -> {
                    Course course = courseMapper.toEntity(courseDTO);
                    recordFound.setName(courseDTO.name());
                    recordFound.setCategory(courseMapper.convertToCategory(courseDTO.category()));
                    recordFound.getLessons().clear();
                    course.getLessons().forEach(lesson -> recordFound.getLessons().add(lesson));
                    return courseMapper.toDTO(repository.save(recordFound));
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(Long id) {
        repository.delete(repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
