package com.devrodrigosnr.crud_spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.devrodrigosnr.crud_spring.model.Course;
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
            c.setCategory("Backend");

            repository.save(c);

			Course c1 = new Course();
            c1.setName("Angular");
            c1.setCategory("Frontend");

            repository.save(c1);
        };
    }
}
