import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { EventsService } from '../services/events.service';
import { Event } from '../model/event';
import { EventDay } from '../model/event-day';
import {Location} from '../../locations/model/location';
import {LocationsService} from '../../locations/services/locations.service';
import {TicketsService} from '../../reports/services/tickets.service';
import {TakenSeats} from '../model/taken-seats';
import {TicketHelp} from '../model/ticket-help';
import {Seat} from '../model/seat';
import {SeatsTicketDTO} from '../../reports/model/seats-ticket';
import {StandTicketDTO} from '../../reports/model/stand-ticker';


@Component({
  selector: 'app-user-event-details',
  templateUrl: './user-event-details.component.html',
  styleUrls: ['./user-event-details.component.css']
})
export class UserEventDetailsComponent implements OnInit {

  id: number;
  event: Event;
  location: Location;
  takenSeats: TakenSeats;
  selectedDay: number;
  selectedTicket: TicketHelp;
  isSuccessful = false;
  isFailed = false;

  constructor(private route: ActivatedRoute, private router: Router, private locationsService: LocationsService,
              private eventsService: EventsService, private ticketsService: TicketsService) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.params.id;

    this.eventsService.getOne(this.id)
      .subscribe(res => {
        this.getLocation(res.body.locationID);
        this.event = res.body as Event;
        this.selectedDay = -1;
        this.onChangeDay();
      }, error => console.log(error));

  }

  book() {

    if (this.selectedTicket.ticketType === 'SEATS') {
      const ticket = new SeatsTicketDTO();
      ticket.columnNumber = this.selectedTicket.column;
      ticket.rowNumber = this.selectedTicket.row;
      if (this.selectedDay === -1) {
        ticket.isSingleDay = false;
        ticket.price = this.selectedTicket.total * this.event.days.length;
        ticket.eventDayID = this.event.days[0].id;

      } else {
        ticket.isSingleDay = true;
        ticket.price = this.selectedTicket.total;
        ticket.eventDayID = this.selectedDay;

      }
      ticket.sectorID = this.selectedTicket.sectorId;
      ticket.eventID = this.event.id;
     // this.ticketsService.addSeatsTicket(ticket);
      this.ticketsService.addSeatsTicket(ticket).subscribe(
        result => {
          this.isSuccessful = true;
          this.isFailed = false;
        },
        err => {
          this.isFailed = true;
          this.isSuccessful = false;

        }
      );
      console.log(ticket);
    } else {
      const ticket = new StandTicketDTO();

      if (this.selectedDay === -1) {
        ticket.isSingleDay = false;
        ticket.price = this.selectedTicket.total * this.event.days.length;
        ticket.eventDayID = this.event.days[0].id;

      } else {
        ticket.isSingleDay = true;
        ticket.price = this.selectedTicket.total;
        ticket.eventDayID = this.selectedDay;

      }
      ticket.sectorID = this.selectedTicket.sectorId;
      ticket.eventID = this.event.id;
      // this.ticketsService.addStandTicket(ticket);
      this.ticketsService.addStandTicket(ticket).subscribe(
        result => {
          this.isSuccessful = true;
          this.isFailed = false;
        },
        err => {
          this.isFailed = true;
          this.isSuccessful = false;

        }
      );
    }
  }

  getLocation(id: number) {
    this.locationsService.getOne(id)
      .subscribe( res => {
        this.location = res.body as Location;
      });
  }

  createTicketHelp(ticketHelp: TicketHelp) {
    this.selectedTicket = ticketHelp;
  }
  onChangeDay() {
    this.selectedTicket = null;

    if (this.selectedDay === -1) {
      this.ticketsService.getTakenSeatsForEvent(this.event.id)
        .subscribe(res => {
          this.takenSeats = res.body as TakenSeats;
        }, error => console.log(error));
    } else {
      this.ticketsService.getTakenSeatsForSector(this.selectedDay)
        .subscribe(res => {
          this.takenSeats = res.body as TakenSeats;
        }, error => console.log(error));
    }

    console.log(this.takenSeats);
  }

  backToList() {
    this.router.navigate(['events']);
  }

}
