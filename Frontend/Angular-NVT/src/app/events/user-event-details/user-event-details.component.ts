import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { EventsService } from '../services/events.service';
import { Event } from '../model/event';
import { EventDay } from '../model/event-day';
import {Location} from "../../locations/model/location";
import {LocationsService} from "../../locations/services/locations.service";
import {TicketsService} from "../../reports/services/tickets.service";
import {TakenSeats} from "../model/taken-seats";


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

  constructor(private route: ActivatedRoute, private router: Router, private locationsService: LocationsService,
              private eventsService: EventsService, private ticketsService: TicketsService) { }

  ngOnInit() {
    this.id = this.route.snapshot.params.id;

    this.eventsService.getOne(this.id)
      .subscribe(res => {
        this.getLocation(res.body.locationID);
        this.event = res.body as Event;
      }, error => console.log(error));

    this.ticketsService.getTakenSeatsForSector(201)
      .subscribe(res => {
        this.takenSeats = res.body as TakenSeats;
      }, error => console.log(error));
  }

  getLocation(id: number) {
    this.locationsService.getOne(id)
      .subscribe( res => {
        this.location = res.body as Location;
      });
  }

  backToList() {
    this.router.navigate(['events']);
  }

}
