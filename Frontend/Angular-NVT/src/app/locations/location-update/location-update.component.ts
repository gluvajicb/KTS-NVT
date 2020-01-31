import { Component, OnInit } from '@angular/core';
import { LocationsService } from '../services/locations.service';
import { ActivatedRoute } from '@angular/router';
import { Location } from '../model/location';

@Component({
  selector: 'app-location-update',
  templateUrl: './location-update.component.html',
  styleUrls: ['./location-update.component.css']
})
export class LocationUpdateComponent implements OnInit {

  id: number;
  location: Location;

  constructor(private route: ActivatedRoute, private locationService: LocationsService) { }

  ngOnInit() {
    // this.location = new Location();

    this.id = this.route.snapshot.params.id;

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

  addNewSector() {
    // save sector

  }

  updateLocation() {
    // update
  }

  deleteSector(sectorTitle: string) {
    let i = 0;

    // dodati proveru
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
