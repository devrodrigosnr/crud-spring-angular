package com.devrodrigosnr.crud_spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Lesson {

@Id
@GeneratedValue(strategy = GenerationType.AUTO)    
private Long id;

@Column(nullable = false, length = 100)
private String name;

@Column(nullable = false, length = 20)
private String youtubeURL;

@ManyToOne(fetch = FetchType.LAZY, optional =false)
@JoinColumn(name = "course_id", nullable = false)
@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
private Course course;

}
