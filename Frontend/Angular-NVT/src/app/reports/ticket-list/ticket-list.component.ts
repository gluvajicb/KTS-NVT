import {Component, OnInit, Input, SimpleChanges} from '@angular/core';
import { Ticket } from '../model/ticket';
import { TicketsService } from '../services/tickets.service';
import {Event} from "../../events/model/event";

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket-list.component.html',
  styleUrls: ['./ticket-list.component.css']
})
export class TicketListComponent implements OnInit {
  @Input() event: Event;

  private ticketList: Ticket[];

  constructor(private ticketsService: TicketsService) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    this.ticketsService.getTicketsForEvent(this.event.id)
      .subscribe( res => {
        this.ticketList = res.body as Ticket[];
      });
  }

  ngOnInit() {
    this.ticketsService.getTicketsForEvent(this.event.id)
      .subscribe( res => {
        this.ticketList = res.body as Ticket[];
      });
  }
}
