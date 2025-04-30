package com.devrodrigosnr.crud_spring.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.devrodrigosnr.crud_spring.enums.Category;
import com.devrodrigosnr.crud_spring.enums.Status;
import com.devrodrigosnr.crud_spring.enums.converters.CategoryConverter;
import com.devrodrigosnr.crud_spring.enums.converters.StatusConverter;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity
@SQLDelete(sql = "UPDATE Course SET status = 'Inativo' WHERE id = ?")
@Where(clause = "status = 'Ativo'")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @NotNull
    @Column(length = 100, nullable = false)
    private String name;

    @NotBlank
    @NotNull
    @Pattern(regexp = "Backend|Frontend")
    @Column(nullable = false)
    @Convert(converter = CategoryConverter.class)
    private Category category;

    @NotBlank
    @NotNull
    @Column(nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.ATIVO;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "course")
    private List<Lesson> lessons = new ArrayList<>();
}
