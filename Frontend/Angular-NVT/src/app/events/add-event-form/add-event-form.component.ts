import { Component, OnInit } from '@angular/core';
import { EventsService } from '../services/events.service';
import { LocationsService } from '../../locations/services/locations.service';
import { Event } from '../model/event';
import { Location } from '../../locations/model/location';

import { EventDay } from '../model/event-day';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { SectorpriceHelp } from '../model/sectorprice-help';
import { Sectorprice } from '../model/sectorprice';


@Component({
  selector: 'app-add-event-form',
  templateUrl: './add-event-form.component.html',
  styleUrls: ['./add-event-form.component.css']
})
export class AddEventFormComponent implements OnInit {

  event: Event;
  locations: Location[];
  selectedLocation: Location;

  constructor(private fb: FormBuilder, private eventsService: EventsService, private locationsService: LocationsService,
              private router: Router) {
    this.event = new Event();
    this.event.days = [];
    this.event.sectorPrices = [];

   }

  ngOnInit() {
    this.locationsService.getAll(1, 100)
      .subscribe( res => {
        this.locations = res.body as Location[];
        this.selectedLocation = this.locations[0];
        console.log(this.selectedLocation);
        for (const sec of this.selectedLocation.sectors) {
          const sp = new Sectorprice();
          sp.sectorID = sec.id;
          this.event.sectorPrices.push(sp);
        }

      });
  }

  onChange(newValue: any) {
    console.log(newValue);
    this.selectedLocation = newValue;
    this.event.sectorPrices = [];
    for (const sec of this.selectedLocation.sectors) {
      const sp = new Sectorprice();
      sp.sectorID = sec.id;
      this.event.sectorPrices.push(sp);
    }

  }

  createSectorPrices(sectorPriceHelp: SectorpriceHelp[]) {
    // this.event.sectorPrices = null;
    this.event.sectorPrices = [];
    for (const sph of sectorPriceHelp) {
      if (sph.enabled) {
        const sp = new Sectorprice();
        sp.sectorID = sph.sectorID;
        sp.price = sph.price;
        this.event.sectorPrices.push(sp);
      }
    }
  }
}
