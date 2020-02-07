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
import { PriceFieldValidatorDirective } from '../directives/price-field-validator.directive';
import { EventPrice } from '../model/event-price';


@Component({
  selector: 'app-add-event-form',
  templateUrl: './add-event-form.component.html',
  styleUrls: ['./add-event-form.component.css']
})
export class AddEventFormComponent implements OnInit {

  event: Event;
  locations: Location[];
  selectedLocation: Location;
  isFailed: boolean;

  constructor(private fb: FormBuilder, private eventsService: EventsService, private locationsService: LocationsService,
              private router: Router) {
    this.event = new Event();
    this.event.days = [];
    this.event.prices = [];
    this.event.eventCategory = 'SHOW';

   }

  ngOnInit() {
    this.isFailed = false;
    this.locationsService.getAll(1, 100)
      .subscribe( res => {
        this.locations = res.body as Location[];
        this.selectedLocation = this.locations[0];
        console.log(this.selectedLocation);
        for (const sec of this.selectedLocation.sectors) {
          const sp = new EventPrice();
          sp.sector = sec;
          this.event.prices.push(sp);
        }

      });
  }

  onChange(newValue: any) {
    console.log(newValue);
    this.selectedLocation = newValue;
    this.event.prices = [];
    for (const sec of this.selectedLocation.sectors) {
      const sp = new EventPrice();
      sp.sector = sec;
      this.event.prices.push(sp);
    }

  }

  createSectorPrices(sectorPriceHelp: SectorpriceHelp[]) {
    // this.event.sectorPrices = null;
    this.event.prices = [];
    for (const sph of sectorPriceHelp) {
      if (sph.enabled) {
        const sp = new EventPrice();

        for ( const s of this.selectedLocation.sectors) {
          if (sph.sectorID === s.id) {
            sp.sector = s;
            break;
          }
        }
        sp.price = sph.price;
        this.event.prices.push(sp);
      }
    }
  }

  addNewDay(day: EventDay) {
    this.eventsService.checkLocationAvailability(this.selectedLocation.id, day.eventdate.toString()).subscribe(
      result => {
        const available = result.body;
        console.log(available);
        if (available) {
          this.event.days.push(day);
          this.isFailed = false;
        } else {
          this.isFailed = true;
        }
      },
      err => {
        this.isFailed = true;
        console.log('wrong');
      }
    );
  }

  onClickDelete(day: EventDay) {
    this.event.days.splice( this.event.days.indexOf(day), 1 );
  }

  addEvent() {
    this.event.locationID = this.selectedLocation.id;
    this.eventsService.add(this.event as Event).subscribe(
      result => {
        this.router.navigate(['events']);
      }
    );
  }

  /*onSelectedChange(value) {
    console.log(value);
    this.event.eventCategory = value;
  }*/
}
