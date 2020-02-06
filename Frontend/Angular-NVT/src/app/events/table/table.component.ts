import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Event } from '../model/event';
import { EventsService } from '../services/events.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-table-event',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableEventComponent implements OnInit {
  @Input() events: Event[];

  @Output() deleteClickedEvent: EventEmitter<number>;

  constructor(private eventService: EventsService, private router: Router) {
    this.deleteClickedEvent = new EventEmitter();
  }

  ngOnInit() {
  }

  eventDetails(id: number) {
    this.router.navigate(['events/details', id]);
  }

  deactivateEvent(id: number) {
    this.deleteClickedEvent.emit(id);
  }
}
