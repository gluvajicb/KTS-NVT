import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-event-container',
  templateUrl: './event-container.component.html',
  styleUrls: ['./event-container.component.css']
})
export class EventContainerComponent implements OnInit {

  search: string;
  filterCategory: string;

  constructor(private router: Router) { }

  ngOnInit() {
    this.filterCategory = 'ANY';
    this.search = '';
  }

  onChange() {
    console.log(this.filterCategory);
  }

  navigateToAdd() {
    this.router.navigate(['events/add']);
  }
}
