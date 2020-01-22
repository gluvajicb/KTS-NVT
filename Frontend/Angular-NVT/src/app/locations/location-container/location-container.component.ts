import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-location-container',
  templateUrl: './location-container.component.html',
  styleUrls: ['./location-container.component.css']
})
export class LocationContainerComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }

  navigateToAdd() {
    this.router.navigate(['locations/add']);
  }
}
