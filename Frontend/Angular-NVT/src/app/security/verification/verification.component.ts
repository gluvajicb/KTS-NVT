import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {SecurityService} from '../services/security/security.service';

@Component({
  selector: 'app-verification',
  templateUrl: './verification.component.html',
  styleUrls: ['./verification.component.css']
})
export class VerificationComponent implements OnInit {
  private email = '';
  private token = '';
  public verified = false;

  constructor(private route: ActivatedRoute, private authService: SecurityService) {

  }

  ngOnInit() {
    this.email = this.route.snapshot.params.email;
    this.token = this.route.snapshot.params.token;
    console.log(this.route.snapshot.params);

    this.authService.verify(this.email, this.token).subscribe(
      data => {
        this.verified = true;
      },
      err => {
        this.verified = false;
      }
    );
  }

}
