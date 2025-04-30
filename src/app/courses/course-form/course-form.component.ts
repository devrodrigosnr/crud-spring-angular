import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NonNullableFormBuilder, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

import { CoursesService } from '../services/courses.service';
import { ActivatedRoute } from '@angular/router';
import { Course } from '../model/course';

@Component({
  selector: 'app-course-form',
  standalone: false,
  templateUrl: './course-form.component.html',
  styleUrl: './course-form.component.scss'
})
export class CourseFormComponent implements OnInit {

  form;

  constructor(private formBuilder: NonNullableFormBuilder,
    private service: CoursesService,
    private _snackBar: MatSnackBar,
    private location: Location,
    private route: ActivatedRoute) {
      this.form = this.formBuilder.group({
        id : [''],
        name: ['',[Validators.required,
          Validators.minLength(5),
          Validators.maxLength(100)]],
        category: ['',[Validators.required]]
      });
  }

  ngOnInit(): void {
    const course: Course = this.route.snapshot.data['course']
    this.form.setValue({
      id: course.id,
      name: course.name,
      category: course.category
    });
    console.log(course);
  }

  onSubmit(): void {
    this.service.save(this.form.value)
    .subscribe(result => this.onSucess(), error => {
      this.onError();
    });
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

  getErrorMessage(fieldName: string) {

    const field = this.form.get(fieldName);

    if(field?.hasError('required')) {
      return 'Campo obrigatório';
    }

    if(field?.hasError('minlength')) {
      const requiredLength: number = field.errors ? field.errors['minlength']['requiredLength'] : 5;
      return `Campo deve ter pelo menos ${requiredLength} caracteres`;
    }

    if(field?.hasError('maxlength')) {
      const requiredLength: number = field.errors ? field.errors['maxlength']['requiredLength'] : 100;
      return `Campo deve ter no máximo ${requiredLength} caracteres`;
    }

    return 'Campo inválido';
  }

}
