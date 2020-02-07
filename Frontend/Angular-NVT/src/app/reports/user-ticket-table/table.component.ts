import {Component, OnInit, Input, Output, EventEmitter, SimpleChanges} from '@angular/core';
import { Ticket } from '../model/ticket';
import { Router } from '@angular/router';
import {TicketsService} from '../services/tickets.service';

@Component({
  selector: 'app-user-table-ticket',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class UserTicketTableComponent implements OnInit {
  @Input() tickets: Ticket[];
  total: number;

  constructor(private ticketsService: TicketsService, private router: Router) {
    this.total = 0;

  }
  ngOnChanges(changes: SimpleChanges): void {
    console.log(this.tickets);
    this.total = 0;
    this.tickets.forEach(ticket => ticket.checked = ticket.paid);
  }
  ngOnInit() {
  }

  calculateTotal(ticket: Ticket) {
    console.log(ticket.checked);

    if (!ticket.checked) {
      this.total += ticket.price;
    } else {
      this.total -= ticket.price;
    }
  }

  pay() {

  }
}
