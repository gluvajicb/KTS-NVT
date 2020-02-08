import { Component, OnInit, Input, OnChanges, SimpleChanges, Output, EventEmitter} from '@angular/core';
import { Sector } from '../../locations/model/sector';

import { SectorpriceHelp } from '../model/sectorprice-help';

@Component({
  selector: 'app-add-sectorprices-form',
  templateUrl: './add-sectorprices-form.component.html',
  styleUrls: ['./add-sectorprices-form.component.css']
})
export class AddSectorpricesFormComponent implements OnInit, OnChanges {

  @Input() sectors: Sector[];
  // sectorPrices: Senctorprice[];

  sectorPriceHelp: SectorpriceHelp[];
  @Output() applySelected: EventEmitter<SectorpriceHelp[]>;

  isFailed: boolean;

  constructor() {
    this.applySelected = new EventEmitter();
    this.sectorPriceHelp = [];
    this.isFailed = false;
  }

  ngOnInit() {
    this.makeSPHelp();
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.makeSPHelp();
  }

  makeSPHelp() {
    this.sectorPriceHelp = [];

    for (const sec of this.sectors) {
      const sph = new SectorpriceHelp();
      sph.title = sec.title;
      sph.enabled = true;
      sph.sectorID = sec.id;
      this.sectorPriceHelp.push(sph);

    }
  }

  sendData() {

    let notValid = false;

    for ( const sp of this.sectorPriceHelp) {
      if (sp.enabled) {
        if (Number(sp.price) > 0) {

        } else {
          notValid = true;
        }
      }

    }
    if (notValid) {
      this.isFailed = true;

    } else {
      this.isFailed = false;
      this.applySelected.emit(this.sectorPriceHelp);
    }
  }

}
