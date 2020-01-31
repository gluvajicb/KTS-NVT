import {Component, OnInit, Input, SimpleChanges} from '@angular/core';
import { Location } from '../model/location';
import { LocationsService } from '../services/locations.service';
import { SearchLocation } from '../model/search-location';


@Component({
  selector: 'app-location-list',
  templateUrl: './location-list.component.html',
  styleUrls: ['./location-list.component.css']
})
export class LocationListComponent implements OnInit {
  private pageSize: number;
  private currentPage: number;
  private totalSize: number;
  private locationList: Location[];
  private allLocations: Location[];
  @Input() search: string;

  constructor(private locationsService: LocationsService) {
    this.pageSize = 8;
    this.currentPage = 1;
    this.totalSize = 15;
   }

   changePage(newPage: number) {
    this.locationsService.getAll(newPage - 1, this.pageSize)
    .subscribe( res => {
      this.locationList = res.body as Location[];
    });
   }

  ngOnInit() {
    this.locationsService.getAll(this.currentPage - 1, this.pageSize)
      .subscribe( res => {
        this.locationList = res.body as Location[];
        this.allLocations = this.locationList;
      });
  }

  reloadData() {
    this.locationsService.getAll(this.currentPage - 1, this.pageSize)
      .subscribe( res => {
        this.locationList = res.body as Location[];
      });
  }

  deleteLocation(id: number) {
    this.locationsService.delete(id)
      .subscribe(
        data => {
          console.log(data);
        }
      );
    this.reloadData();
  }

  ngOnChanges(changes: SimpleChanges) {
    let searchValue = this.search;

    this.locationList = this.allLocations.filter(function(el) {
      return el.title.toLowerCase().includes(searchValue.toLowerCase())  || el.address.toLowerCase().includes(searchValue.toLowerCase());
    });
  }

}
