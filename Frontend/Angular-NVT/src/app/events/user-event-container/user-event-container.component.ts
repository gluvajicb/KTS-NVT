import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {Sectorprice} from "../model/sectorprice";

@Component({
  selector: 'app-user-event-container',
  templateUrl: './user-event-container.component.html',
  styleUrls: ['./user-event-container.component.css']
})
export class UserEventContainerComponent implements OnInit {

  search: string;
  filterCategory: string;
  model: number;

  constructor(private router: Router) { }

  ngOnInit() {
    this.filterCategory = 'ANY';
  }

  onChange() {
    console.log(this.filterCategory);
    }
}
