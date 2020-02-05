import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { EventsService } from '../services/events.service';
import { Event } from '../model/event';
import { EventDay } from '../model/event-day';
import {Location} from "../../locations/model/location";
import {LocationsService} from "../../locations/services/locations.service";
import {TicketsService} from "../../reports/services/tickets.service";
import {TakenSeats} from "../model/taken-seats";
import {TicketHelp} from "../model/ticket-help";
import {Seat} from "../model/seat";
import {SeatsTicketDTO} from "../../reports/model/seats-ticket";
import {StandTicketDTO} from "../../reports/model/stand-ticker";


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

  constructor(private route: ActivatedRoute, private router: Router, private locationsService: LocationsService,
              private eventsService: EventsService, private ticketsService: TicketsService) {
  }

  ngOnInit() {
    this.id = this.route.snapshot.params.id;

    this.eventsService.getOne(this.id)
      .subscribe(res => {
        this.getLocation(res.body.locationID);
        this.event = res.body as Event;
        this.selectedDay = this.event.days[0].id;
      }, error => console.log(error));

  }

  book() {

    if (this.selectedTicket.ticketType === 'SEATS') {
      let ticket = new SeatsTicketDTO();
      ticket.columnNumber = this.selectedTicket.column;
      ticket.rowNumber = this.selectedTicket.row;
      ticket.isSingleDay = true;
      ticket.price = this.selectedTicket.total;
      ticket.sectorID = this.selectedTicket.sectorId;
      ticket.eventDayID = this.selectedDay;
      ticket.eventID = this.event.id;
      this.ticketsService.addSeatsTicket(ticket);
      console.log(ticket);
    } else {
      let ticket = new StandTicketDTO();
      ticket.isSingleDay = true;
      ticket.price = this.selectedTicket.total;
      ticket.sectorID = this.selectedTicket.sectorId;
      ticket.eventDayID = this.selectedDay;
      ticket.eventID = this.event.id;
      this.ticketsService.addStandTicket(ticket);
      console.log('STANDD');
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
    this.ticketsService.getTakenSeatsForSector(this.selectedDay)
      .subscribe(res => {
        this.takenSeats = res.body as TakenSeats;
      }, error => console.log(error));

    console.log(this.takenSeats);
  }

  backToList() {
    this.router.navigate(['events']);
  }

}
