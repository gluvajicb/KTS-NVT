import { Component, OnInit } from '@angular/core';
import { LocationsService } from '../services/locations.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '../model/location';
import { SectorService } from '../services/sector.service';
import { Sector } from '../model/sector';
import { Route } from '@angular/compiler/src/core';

@Component({
  selector: 'app-location-update',
  templateUrl: './location-update.component.html',
  styleUrls: ['./location-update.component.css']
})
export class LocationUpdateComponent implements OnInit {

  id: number;
  location: Location;
  sectorForDelete: string;
  sectoToAdd: Sector;
  numOfSectors: number;
  hasUpcomingEvents: boolean;

  constructor(private route: ActivatedRoute, private locationService: LocationsService,
              private sectorService: SectorService, private router: Router) {
    this.sectorForDelete = '';
  }

  ngOnInit() {
    // this.location = new Location();

    this.id = this.route.snapshot.params.id;
    this.numOfSectors = 0;

    this.locationService.hasUpcomingEvents(this.id)
    .subscribe (
      data => {
        this.hasUpcomingEvents = data.body;
      }
    );

    this.locationService.getOne(this.id)
      .subscribe (data => {
        this.location = data.body;
        console.log(data);
        console.log(this.location);
    });

  }

  editSectors(canvas: any) {
    console.log('edit');
    console.log(canvas);

    for (const sec of this.location.sectors) {
      for (const canvasSector of canvas.objects) {
        if (sec.type === 'stand') {
          if (canvasSector.type === 'table') {
            if (canvasSector.objects[1].text === sec.title) {
              console.log('table');
              sec.top = canvasSector.top;
              sec.left = canvasSector.left;
              sec.angle = canvasSector.angle;
              sec.width = canvasSector.width * canvasSector.scaleX;
              sec.height = canvasSector.height * canvasSector.scaleY;
            }
          }
        } else {
          if (canvasSector.type === 'group') {
            if (canvasSector.objects[0].text === sec.title || canvasSector.objects[1].text === sec.title) {
              sec.top = canvasSector.top;
              sec.left = canvasSector.left;
              sec.angle = canvasSector.angle;
              sec.width = canvasSector.width * canvasSector.scaleX;
              sec.height = canvasSector.height * canvasSector.scaleY;
            }
          }
        }
      }
    }

  }

  addNewSector(sector: Sector) {
    this.sectorService.add(sector as Sector, this.location.id).subscribe(
      result => {
        console.log('add');
        this.numOfSectors += 1;
        console.log(result);
        this.location.sectors.push(result.body as Sector);
      }
    );

  }

  updateLocation() {
    this.locationService.update(this.location as Location).subscribe(
      result => {
        console.log(result);
        this.router.navigate(['locations']);
      }
    );
  }

  deleteSector(sectorTitle: string) {
    let i = 0;
    console.log('title ' + sectorTitle);
    // dodati proveru
    // ako je moguce obrisati setovati sectorForDelete na title sektora
    let sectorId: number;
    for (const sec of this.location.sectors) {
      if (sec.title === sectorTitle) {
        sectorId = sec.id;
        console.log('sec id ' + sectorId);
        break;
      }
      i ++;
    }

    this.sectorService.delete(sectorId).subscribe(
      result => {
        console.log('deleted');
      }, err => {
        console.log('not deleted');
      }
    );
    if ( i < this.location.sectors.length) {
      this.location.sectors.splice(i, 1);
    }

    this.sectorForDelete = sectorTitle;
  }

}
