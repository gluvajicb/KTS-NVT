import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Sector } from '../model/sector';
import { LocationsService} from '../services/locations.service';
import { Location } from '../model/location';
import { FormGroup, FormBuilder, Validators, AbstractControl} from '@angular/forms';
import { SectorService } from '../services/sector.service';


@Component({
  selector: 'app-add-sector',
  templateUrl: './add-sector.component.html',
  styleUrls: ['./add-sector.component.css']
})
export class AddSectorComponent implements OnInit {

  @Output() newSector: EventEmitter<Sector>;

  sector: Sector;
  sectorForm: FormGroup;

  showMaxGuestsInput = false;
  showRowColInput = false;

  constructor(private fb: FormBuilder, private sectorService: SectorService) {
    this.newSector = new EventEmitter();
    this.createForm();
  }

  ngOnInit() {
    // this.sector = new Sector();
  }

  createForm() {
    this.sectorForm = this.fb.group({
      title: ['', [Validators.required]],
      type: ['', Validators.required],
      max_guests: [''],
      row_num: [''],
      column_num: [''],
    });

    this.sectorForm.get('type').valueChanges.subscribe(val => {
      if (val === 'stand') { // for setting validations
        this.sectorForm.get('max_guests').setValidators([Validators.required]);
        this.sectorForm.get('row_num').clearValidators();
        this.sectorForm.get('row_num').setValue('');
        this.sectorForm.get('column_num').clearValidators();
        this.sectorForm.get('column_num').setValue('');
      }
      if (val === 'seats') { // for clearing validations
        this.sectorForm.get('max_guests').clearValidators();
        this.sectorForm.get('max_guests').setValue('');
        this.sectorForm.get('row_num').setValidators(Validators.required);
        this.sectorForm.get('column_num').setValidators(Validators.required);
      }
  });
  }


   radioSeatsCheck() {
    this.showRowColInput = true;
    this.showMaxGuestsInput = false;
    // this.sector.type = 'seats';
    // this.sector.max_guests = -1;
  }

  radioStandCheck() {
    this.showRowColInput = false;
    this.showMaxGuestsInput = true;
    // this.sector.type = 'stand';
    // this.sector.column_num = -1;
    // this.sector.row_num = -1;
  }

  onSubmit() {
    console.log('add sector');
    this.sector = this.sectorForm.value;
    this.sector.top = 100;
    this.sector.left = 100;
    this.sector.height = 100;
    this.sector.width = 100;
    this.newSector.emit(this.sector);

    this.sectorForm.reset();
  }

}
