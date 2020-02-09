import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-verification',
  templateUrl: './verification.component.html',
  styleUrls: ['./verification.component.css']
})
export class VerificationComponent implements OnInit {
  verified = false;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.verified = this.route.snapshot.params.verified;
    console.log(this.route.snapshot.params);
  }

}
