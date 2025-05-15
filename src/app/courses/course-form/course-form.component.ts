import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Form, FormGroup, NonNullableFormBuilder, UntypedFormArray, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

import { CoursesService } from '../services/courses.service';
import { ActivatedRoute } from '@angular/router';
import { Course } from '../model/course';
import { Lesson } from '../model/lesson';
import { FormUtilsService } from '../../shared/form/form-utils.service';

@Component({
  selector: 'app-course-form',
  standalone: false,
  templateUrl: './course-form.component.html',
  styleUrl: './course-form.component.scss'
})
export class CourseFormComponent implements OnInit {

  form!: FormGroup;

  constructor(private formBuilder: NonNullableFormBuilder,
    private service: CoursesService,
    private _snackBar: MatSnackBar,
    private location: Location,
    private route: ActivatedRoute,
    public formUtils: FormUtilsService) {
  }

  ngOnInit(): void {
    const course: Course = this.route.snapshot.data['course'];
    this.form = this.formBuilder.group({
      id : [course.id],
      name: [course.name, [Validators.required,
        Validators.minLength(5),
        Validators.maxLength(100)]],
      category: [course.category, [Validators.required]],
      lessons: this.formBuilder.array(this.retrieveLessons(course), Validators.required)
    });
  }

  onSubmit(): void {
    if (this.form.valid) {
      this.service.save(this.form.value)
      .subscribe(result => this.onSucess(), error =>
        this.onError());
    } else {
      this.formUtils.validateAllFormFields(this.form);
    }
  }


  onCancel(): void {
    this.location.back();
  }

  private onSucess(){
    this._snackBar.open('Curso salvo com sucesso', '', { duration: 3000 });
    this.onCancel();
  }

  private onError(){
    this._snackBar.open('Erro ao salvar curso', '', { duration: 3000 });
  }

  private createLesson(lesson: Lesson = { id: '', name: '', youtubeURL: '' }) {
    return this.formBuilder.group({
      id: [lesson.id],
      name: [lesson.name, [Validators.required,
        Validators.minLength(5),
        Validators.maxLength(100)]],
        youtubeURL: [lesson.youtubeURL, [Validators.required,
        Validators.minLength(10),
        Validators.maxLength(11)]]
    });
  }

  private retrieveLessons(course: Course) {
    const lessons = [];
    if(course?.lessons) {
      course.lessons.forEach(lesson => lessons.push(this.createLesson(lesson)));
    } else {
      lessons.push(this.createLesson());
    }
    return lessons;
  }

  getLessonsFormArray() {
    return (<UntypedFormArray>this.form.get('lessons')).controls;
  }

  addNewLesson() {
    const lessons = <UntypedFormArray>this.form.get('lessons');
    lessons.push(this.createLesson());
  }

  removeLesson(index: number) {
    const lessons = <UntypedFormArray>this.form.get('lessons');
    lessons.removeAt(index);
  }
}
