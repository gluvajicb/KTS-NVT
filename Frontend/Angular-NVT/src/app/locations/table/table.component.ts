import { Component, OnInit, Input } from '@angular/core';
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

  constructor(private locationService: LocationsService, private router: Router) { }

  ngOnInit() {
  }

  deleteLocation(id: number) {
    this.locationService.delete(id);
  }

  locationDetails(id: number) {
    console.log(id);
    this.router.navigate(['locations/details', id]);
  }
}
