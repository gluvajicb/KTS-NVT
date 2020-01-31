import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Sector } from '../model/sector';
import { LocationsService} from '../services/locations.service';
import { Location } from '../model/location';
import { FormGroup, FormBuilder, Validators, AbstractControl} from '@angular/forms';
import { SectorService } from '../services/sector.service';
import { sectorTypeFormValidator } from '../directives/sector-type-form.directive';


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
  }

  createForm() {
    this.sectorForm = this.fb.group({
      title: ['', [Validators.required]],
      type: ['', Validators.required],
      max_guests: [''],
      row_num: [''],
      column_num: [''],
    }, {validators: sectorTypeFormValidator});

  }


   radioSeatsCheck() {
    this.showRowColInput = true;
    this.showMaxGuestsInput = false;
  }

  radioStandCheck() {
    this.showRowColInput = false;
    this.showMaxGuestsInput = true;
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
