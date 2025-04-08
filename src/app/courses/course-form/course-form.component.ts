import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NonNullableFormBuilder } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';

import { CoursesService } from '../services/courses.service';

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
    private location: Location) {
      this.form = this.formBuilder.group({
        name: [''],
        category: ['']
      });
  }

  ngOnInit(): void {
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

}
