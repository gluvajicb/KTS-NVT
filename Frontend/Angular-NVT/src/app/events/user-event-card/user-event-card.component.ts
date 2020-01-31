import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Event } from '../model/event';
import { EventsService } from '../services/events.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-event-card',
  templateUrl: './user-event-card.component.html',
  styleUrls: ['./user-event-card.component.css']
})
export class UserEventCardComponent implements OnInit {
  @Input() events: Event[];

  @Output() deleteClickedEvent: EventEmitter<number>;

  constructor(private eventService: EventsService, private router: Router) {
    this.deleteClickedEvent = new EventEmitter();
  }

  ngOnInit() {
  }

  eventDetails(id: number) {
    this.router.navigate(['user/events/details', id]);
  }
}
