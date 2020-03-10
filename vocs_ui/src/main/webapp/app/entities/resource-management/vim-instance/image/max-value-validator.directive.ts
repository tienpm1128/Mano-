import { Directive, Input } from '@angular/core';
import { NG_VALIDATORS, Validator, FormControl } from '@angular/forms';

@Directive({
  selector: '[maxValue][formControl], [maxValue][formControlName], [maxValue][ngModel]',
  providers: [{ provide: NG_VALIDATORS, useExisting: MaxValueValidator, multi: true }]
})
export class MaxValueValidator implements Validator {
  @Input()
  maxValue: number;

  validate(control: FormControl): { [key: string]: any } {
    const value = control.value;
    return value > this.maxValue ? { maxValue: true } : null;
  }
}
