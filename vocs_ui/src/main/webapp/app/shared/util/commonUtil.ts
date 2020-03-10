import { Injectable } from '@angular/core';

@Injectable()
export class CommonUntil {
  randomizeColor() {
    const r = Math.floor(Math.random() * 255);
    const g = Math.floor(Math.random() * 255);
    const b = Math.floor(Math.random() * 255);
    return 'rgb(' + r + ',' + g + ',' + b + ')';
  }

  checkNumber(min: number, max: number, value: number) {
    if (value !== null) {
      if (value >= min && value <= max) {
        return false;
      }
      return true;
    }
    return false;
  }

  blockSpecial(event: any) {
    let k;
    k = event.charCode;
    return (k > 64 && k < 91) || (k > 96 && k < 123) || k === 8 || k === 32 || (k >= 48 && k <= 57);
  }

  removeAccents(fileName: string) {
    return fileName
      .toLowerCase()
      .replace(new RegExp(/[đ]/g), 'd')
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '');
  }
}
