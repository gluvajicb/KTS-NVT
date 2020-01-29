import { Component, OnInit } from '@angular/core';
import { EventsService } from '../services/events.service';
import { LocationsService } from '../../locations/services/locations.service';
import { Event } from '../model/event';
import { Location } from '../../locations/model/location';

import { EventDay } from '../model/event-day';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';


@Component({
  selector: 'app-add-event-form',
  templateUrl: './add-event-form.component.html',
  styleUrls: ['./add-event-form.component.css']
})
export class AddEventFormComponent implements OnInit {

  event: Event;
  locations: Location[];
  constructor(private fb: FormBuilder, private eventsService: EventsService, private locationsService: LocationsService,
              private router: Router) {
    this.event = new Event();
    this.event.days = [];
   }

  ngOnInit() {
    this.locationsService.getAll(1, 100)
      .subscribe( res => {
        this.locations = res.body as Location[];
        console.log(this.locations);
      });
  }
}
