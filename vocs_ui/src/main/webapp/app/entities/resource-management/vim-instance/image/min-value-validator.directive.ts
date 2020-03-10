import { Directive, Input } from '@angular/core';
import { NG_VALIDATORS, Validator, FormControl } from '@angular/forms';

@Directive({
  selector: '[maxValue][formControl], [maxValue][formControlName], [maxValue][ngModel]',
  providers: [{ provide: NG_VALIDATORS, useExisting: MinValueValidator, multi: true }]
})
export class MinValueValidator implements Validator {
  @Input()
  minValue: number;

  validate(control: FormControl): { [key: string]: any } {
    const value = control.value;
    return value < this.minValue ? { maxValue: true } : null;
  }
}
