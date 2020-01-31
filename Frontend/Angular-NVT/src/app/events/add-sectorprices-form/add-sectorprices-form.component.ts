import { Component, OnInit, Input, OnChanges, SimpleChanges, Output, EventEmitter} from '@angular/core';
import { Sector } from '../../locations/model/sector';
import { Sectorprice } from '../model/sectorprice';
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

  constructor() {
    this.applySelected = new EventEmitter();
    this.sectorPriceHelp = [];
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
    this.applySelected.emit(this.sectorPriceHelp);
  }

}