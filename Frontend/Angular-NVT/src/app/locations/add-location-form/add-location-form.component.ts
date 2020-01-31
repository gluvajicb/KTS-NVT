import { Component, OnInit } from '@angular/core';
import { LocationsService } from '../services/locations.service';
import { Location } from '../model/location';
import { Sector } from '../model/sector';
import { FormGroup, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';


@Component({
  selector: 'app-add-location-form',
  templateUrl: './add-location-form.component.html',
  styleUrls: ['./add-location-form.component.css']
})
export class AddLocationFormComponent implements OnInit {

  location: Location;
  numOfSectors: number;
  // locationForm: FormGroup;

  constructor(private fb: FormBuilder, private locationService: LocationsService, private router: Router) {
    this.location = new Location();
    this.location.sectors = [];
    this.numOfSectors = 0;
   }

  ngOnInit() {
  }

  addNewSector(sector: Sector) {
    this.location.sectors.push(sector);
    this.numOfSectors++;
    console.log('NUm ' + this.numOfSectors);
  }

  addLocation() {
    console.log('add location');
    console.log(this.location);

    this.locationService.add(this.location as Location).subscribe(
      result => {
        console.log(result);
        this.router.navigate(['locations']);
      }
    );
  }

  editSectors(canvas: any) {
    console.log('edit');
    console.log(canvas);

    for (const sec of this.location.sectors) {
      for (const canvasSector of canvas.objects) {
        if (sec.type === 'stand') {
          if (canvasSector.type === 'table') {
            if (canvasSector.objects[1].text === sec.title) {
              sec.top = canvasSector.top;
              sec.left = canvasSector.left;
              sec.angle = canvasSector.angle;
              sec.width = canvasSector.width * canvasSector.scaleX;
              sec.height = canvasSector.height * canvasSector.scaleY;
            }
          }
        } else {
          if (canvasSector.type === 'group') {
            if (canvasSector.objects[0].text === sec.title) {
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

  deleteSector(sectorTitle: string) {
    let i = 0;

    for (const sec of this.location.sectors) {
      if (sec.title === sectorTitle) {
        break;
      }
      i ++;
    }
    if ( i < this.location.sectors.length) {
      this.location.sectors.splice(i, 1);
    }
  }

}
