import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LocationsService } from '../services/locations.service';
import { Location } from '../model/location';
import { Sector } from '../model/sector';


@Component({
  selector: 'app-location-details',
  templateUrl: './location-details.component.html',
  styleUrls: ['./location-details.component.css']
})
export class LocationDetailsComponent implements OnInit {

  id: number;
  location: Location;

  constructor(private route: ActivatedRoute, private router: Router,
              private locationService: LocationsService) { }

  ngOnInit() {
    this.id = this.route.snapshot.params.id;

    this.locationService.getOne(this.id)
      .subscribe(res => {
        this.location = res.body as Location;
        // this.location.sectors = res.body.sectors as Sector[];
      }, error => console.log(error));

  }

  backToList() {
    this.router.navigate(['locations']);
  }

}
