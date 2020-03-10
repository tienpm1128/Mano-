/**
 * Angular bootstrap Date adapter
 */
import { Injectable } from '@angular/core';

@Injectable()
export class InputUntil {
  isDigit(event) {
    const keyCode = event.keyCode;
    const allowed_keys = [
      8, // backspace
      46, // delete
      37,
      39, // left and right arrow
      35,
      36 // home and end
    ];
    return (keyCode >= 48 && keyCode <= 57) || (keyCode >= 96 && keyCode <= 105) || allowed_keys.indexOf(keyCode) !== -1;
  }
}
