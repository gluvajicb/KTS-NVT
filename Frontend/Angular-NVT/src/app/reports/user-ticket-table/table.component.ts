import {Component, OnInit, Input, Output, EventEmitter, SimpleChanges, ViewChild} from '@angular/core';
import {Ticket} from '../model/ticket';
import {Router} from '@angular/router';
import {TicketsService} from '../services/tickets.service';
import {PaypalPaymentComponent} from '../../payment/paypal-payment/paypal-payment.component';

@Component({
  selector: 'app-user-table-ticket',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class UserTicketTableComponent implements OnInit {
  @Input() tickets: Ticket[];
  @ViewChild(PaypalPaymentComponent, {static: false}) paypal: PaypalPaymentComponent;
  total: number;
  ticketsIds: number[];

  constructor(private ticketsService: TicketsService, private router: Router) {
    this.total = 0;
    this.ticketsIds = [];
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
      this.ticketsIds.push(ticket.id);
    } else {
      this.total -= ticket.price;
      const index = this.ticketsIds.indexOf(ticket.id, 0);
      if (index > -1) {
        this.ticketsIds.splice(index, 1);
      }
    }

    this.paypal.reload(this.ticketsIds);
  }
}
