import { Injectable } from '@angular/core';
import { UntypedFormArray, UntypedFormControl, UntypedFormGroup } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class FormUtilsService {

  constructor() { }

  validateAllFormFields(formGroup: UntypedFormGroup | UntypedFormArray) {
    Object.keys(formGroup.controls).forEach(field => {
      const control = formGroup.get(field);
      if (control instanceof UntypedFormControl) {
        control.markAsTouched({ onlySelf: true });
      } else if (control instanceof UntypedFormGroup || control instanceof UntypedFormArray) {
        control.markAsTouched({ onlySelf: true });
        this.validateAllFormFields(control);
      }
    });
  }

  getErrorMessage(formGroup: UntypedFormGroup ,fieldName: string) {
    const field = <UntypedFormControl>formGroup.get(fieldName);
    return this.getErrorMessageFormField(field);
  }

  getErrorMessageFormField(field: UntypedFormControl) {
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

  getFormArrayFieldErrorMessage(formGroup: UntypedFormGroup, formArrayName: string,
    fieldName: string, index: number) {
    const formArray = <UntypedFormArray>formGroup.get(formArrayName);
    const field = <UntypedFormControl>formArray.controls[index].get(fieldName);
    return this.getErrorMessageFormField(field);
  }

  isFormArrayRequired(formGroup: UntypedFormGroup, formArrayName: string) {
    const formArray = <UntypedFormArray>formGroup.get(formArrayName);
    return !formArray.valid && formArray.hasError('required') && formArray.touched;
  }
}
