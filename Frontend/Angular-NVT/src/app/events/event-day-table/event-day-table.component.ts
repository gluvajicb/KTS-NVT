import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { EventDay } from '../model/event-day';
import { EventsService } from '../services/events.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-table-event-day',
  templateUrl: './event-day-table.component.html',
  styleUrls: ['./event-day-table.component.css']
})
export class TableEventDayComponent implements OnInit {
  @Input() days: EventDay[];
  constructor(private eventService: EventsService, private router: Router) {
  }

  ngOnInit() {
  }
}
