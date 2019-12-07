import { Pipe, PipeTransform } from '@angular/core';
import { format } from 'date-fns';

@Pipe({
  name: 'formatDate'
})

export class FormatDatePipe implements PipeTransform {

  transform(value: number, dateFormat: string): string {
    return format(value, dateFormat);
  }
}
