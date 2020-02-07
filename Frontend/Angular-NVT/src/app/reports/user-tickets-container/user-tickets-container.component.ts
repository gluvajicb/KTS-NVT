import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Event } from './../../events/model/event';
import { EventsService} from "../../events/services/events.service";
import {Sectorprice} from "../../events/model/sectorprice";
import {Ticket} from "../model/ticket";
import {TicketsService} from "../services/tickets.service";

@Component({
  selector: 'app-user-tickets-container',
  templateUrl: './user-tickets-container.component.html',
  styleUrls: ['./user-tickets-container.component.css']
})
export class UserTicketsContainerComponent implements OnInit {

  ticketList: Ticket[];
  constructor(private router: Router, private ticketsService: TicketsService) {
  }

  ngOnInit() {
    this.ticketsService.getUsersTickets()
      .subscribe(res => {
        this.ticketList = res.body as Ticket[];
      });
  }

}
