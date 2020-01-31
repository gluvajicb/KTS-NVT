import { Directive } from '@angular/core';
import { NG_VALIDATORS, Validator, AbstractControl, ValidationErrors } from '@angular/forms';

@Directive({
  selector: '[appSectorPriceFormValidator]',
  providers: [{ provide: NG_VALIDATORS, useExisting:  SectorPriceFormValidatorDirective, multi: true }]
})
export class SectorPriceFormValidatorDirective implements Validator {

  validate(control: AbstractControl): ValidationErrors {

    console.log(control.get('price') + '*****');

    return null;
  }

  constructor() { }

}
