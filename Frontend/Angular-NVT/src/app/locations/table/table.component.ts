import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Location } from '../model/location';
import { LocationsService } from '../services/locations.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-table-location',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.css']
})
export class TableComponent implements OnInit {
  @Input() locations: Location[];

  @Output() deleteClickedEvent: EventEmitter<number>;

  constructor(private locationService: LocationsService, private router: Router) {
    this.deleteClickedEvent = new EventEmitter();
  }

  ngOnInit() {
  }

  onClickDelete(id: number) {
    this.deleteClickedEvent.emit(id);
  }

  locationDetails(id: number) {
    console.log(id);
    this.router.navigate(['locations/details', id]);
  }
}
