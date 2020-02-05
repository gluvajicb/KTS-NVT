import {Component, OnInit, Input, SimpleChanges} from '@angular/core';
import { Event } from '../model/event';
import { EventsService } from '../services/events.service';


@Component({
  selector: 'app-user-event-list',
  templateUrl: './user-event-list.component.html',
  styleUrls: ['./user-event-list.component.css']
})
export class UserEventListComponent implements OnInit {
  private pageSize: number;
  private currentPage: number;
  private totalSize: number;
  private eventList: Event[];
  private allEvents: Event[];
  @Input() filterCategory: string;
  @Input() search: string;


  constructor(private eventsService: EventsService) {
    this.pageSize = 8;
    this.currentPage = 1;
    this.totalSize = 15;
   }

  ngOnInit() {
    this.eventsService.getUpcoming()
      .subscribe( res => {
        this.eventList = res.body as Event[];
        this.allEvents = this.eventList;
      });
  }

  ngOnChanges(changes: SimpleChanges) {
    let category = this.filterCategory;
    let searchValue = this.search;

    if (this.filterCategory !== 'ANY'  && this.filterCategory !== 'ANY CATEGORY') {
      this.eventList = this.allEvents.filter( function(el) {
        return el.eventCategory === category && el.title.toLowerCase().includes(searchValue.toLowerCase());
      });
    }else{
      console.log(searchValue);
      this.eventList = this.allEvents.filter(function(el) {
        return el.title.toLowerCase().includes(searchValue.toLowerCase());
      });
    }
  }
}
