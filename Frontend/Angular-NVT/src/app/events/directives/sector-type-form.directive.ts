import { ValidatorFn, FormGroup, ValidationErrors } from '@angular/forms';

export const sectorTypeFormValidator: ValidatorFn = (control: FormGroup):
ValidationErrors | null => {
  const type = control.get('type');
  const rows = control.get('row_num');
  const cols = control.get('column_num');
  const guests = control.get('max_guests');

  if (type.value === 'seats' && rows.value > 0 && cols.value > 0) {
    return null;
  }

  if (type.value === 'stand' && guests.value > 0) {
    return null;
  }

  return {sectorTypeFormValidator: true};
};
