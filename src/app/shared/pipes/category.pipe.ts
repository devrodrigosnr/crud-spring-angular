import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'category',
  standalone: false
})
export class CategoryPipe implements PipeTransform {

  transform(value: string): string {
    switch (value) {
      case 'Frontend': return 'code';
      case 'Backend': return 'computer';
    }
    return 'code';
  }
}
