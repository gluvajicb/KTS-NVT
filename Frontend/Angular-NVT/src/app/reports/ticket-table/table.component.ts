import {Component, OnInit, Input, Output, EventEmitter, SimpleChanges} from '@angular/core';
import { Ticket } from '../model/ticket';
import { Router } from '@angular/router';
import {TicketsService} from '../services/tickets.service';

@Component({
  selector: 'app-table-ticket',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TicketTableComponent implements OnInit {
  @Input() tickets: Ticket[];
  total: number;

  constructor(private ticketsService: TicketsService, private router: Router) {
    this.total = 0;
  }
  ngOnChanges(changes: SimpleChanges): void {
    console.log(this.tickets);
    this.total = 0;
    this.getTotal();
  }
  ngOnInit() {
  }
  getTotal() {
    for( let t of this.tickets) {
      this.total += t.price;
    }
  }
}
