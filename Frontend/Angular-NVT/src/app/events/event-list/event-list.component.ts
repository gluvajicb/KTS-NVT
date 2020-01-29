import { Component, OnInit, Input} from '@angular/core';
import { Event } from '../model/event';
import { EventsService } from '../services/events.service';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.css']
})
export class EventListComponent implements OnInit {
  private pageSize: number;
  private currentPage: number;
  private totalSize: number;
  private eventList: Event[];

  constructor(private eventsService: EventsService) {
    this.pageSize = 8;
    this.currentPage = 1;
    this.totalSize = 15;
   }

  ngOnInit() {
    this.eventsService.getAll(this.currentPage - 1, this.pageSize)
      .subscribe( res => {
        this.eventList = res.body as Event[];
        console.log(this.eventList);
      });
  }


  deleteEvent(id: number) {
  }

}
