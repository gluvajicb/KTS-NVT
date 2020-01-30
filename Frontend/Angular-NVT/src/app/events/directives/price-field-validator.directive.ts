import { Directive } from '@angular/core';
import { NG_VALIDATORS, Validator, AbstractControl, ValidationErrors } from '@angular/forms';

@Directive({
  selector: '[appPriceFieldValidator]',
  providers: [{ provide: NG_VALIDATORS, useExisting:  PriceFieldValidatorDirective, multi: true }]
})
export class PriceFieldValidatorDirective implements Validator {

  validate(control: AbstractControl): ValidationErrors {
    if (control.value) {
      if (control.value >= 0) {

        return null;
      } else {
        return {PriceFieldValidatorDirective: true};
      }
    }
  }

  constructor() { }

}
