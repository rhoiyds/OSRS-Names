import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'osrsCurrency' })
export class OsrsCurrencyPipe implements PipeTransform {
  transform(value: number): string {
    if (value >= 1000000000) {
      return Math.floor(value / 1000000000) + 'b';
    }
    if (value >= 1000000) {
      return Math.floor(value / 1000000) + 'm';
    }
    if (value >= 1000) {
      return Math.floor(value / 1000) + 'k';
    }
    return value.toString();
  }
}
