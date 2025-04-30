package com.devrodrigosnr.crud_spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.devrodrigosnr.crud_spring.enums.Category;
import com.devrodrigosnr.crud_spring.model.Course;
import com.devrodrigosnr.crud_spring.model.Lesson;
import com.devrodrigosnr.crud_spring.repository.CourseRepository;

@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	@Bean
    CommandLineRunner initDataBase(CourseRepository repository){
        return args -> {
            repository.deleteAll(); 

            Course c = new Course();
            c.setName("Spring Boot");
            c.setCategory(Category.BACKEND);

            repository.save(c);

			Course c1 = new Course();
            c1.setName("Angular");
            c1.setCategory(Category.FRONTEND);

            Lesson l = new Lesson();
            l.setName("Introdução");
            l.setYoutubeURL("watch.?aasas");
            l.setCourse(c1);
            c1.getLessons().add(l);

            Lesson l1 = new Lesson();
            l1.setName("Introdução");
            l1.setYoutubeURL("watch.?aasas");
            l1.setCourse(c1);
            c1.getLessons().add(l1);

            repository.save(c1);
        };
    }
}
