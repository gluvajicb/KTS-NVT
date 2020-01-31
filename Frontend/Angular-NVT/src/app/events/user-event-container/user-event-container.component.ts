import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-event-container',
  templateUrl: './user-event-container.component.html',
  styleUrls: ['./user-event-container.component.css']
})
export class UserEventContainerComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }
}
