import { Component, OnInit } from '@angular/core';
import { EventsService } from '../services/events.service';
import { Event } from '../model/event';
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

  constructor(private fb: FormBuilder, private eventsService: EventsService, private router: Router) {
    this.event = new Event();
    this.event.days = [];
   }

  ngOnInit() {
  }
}
