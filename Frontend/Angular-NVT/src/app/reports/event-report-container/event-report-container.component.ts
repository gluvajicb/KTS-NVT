import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Event } from './../../events/model/event';
import { EventsService} from "../../events/services/events.service";
import {Sectorprice} from "../../events/model/sectorprice";

@Component({
  selector: 'app-event-report-container',
  templateUrl: './event-report-container.component.html',
  styleUrls: ['./event-report-container.component.css']
})
export class EventReportContainerComponent implements OnInit {

  selectedEvent: Event;
  events: Event[];

  constructor(private router: Router, private eventsService: EventsService) {
  }

  ngOnInit() {
    this.eventsService.getAll(1, 100)
      .subscribe(res => {
        this.events = res.body as Event[];
        this.selectedEvent = this.events[0];
      });
  }

  onChange(newValue: any) {
    this.selectedEvent = newValue;
  }
}
