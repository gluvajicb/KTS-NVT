
import { ValidatorFn, FormGroup, ValidationErrors, AbstractControl } from '@angular/forms';

export function dateNotPastValidatorDirective(): ValidatorFn {
  return (c: AbstractControl) => {
    const now = new Date();
    const eventdate = new Date(c.value);
    const valid = (eventdate > now);
    if (valid) {
      return null;
    } else {
      return {dateNotPastValidator: true};
    }
  };
}
