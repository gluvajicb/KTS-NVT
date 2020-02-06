import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from '../services/token-storage/token-storage.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {UserService} from '../services/user/user.service';

const USER_API = 'http://localhost:8080/user/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: any = {};

  constructor(private userService: UserService, private tokenStorageService: TokenStorageService) {
  }

  ngOnInit() {
    const userId = this.tokenStorageService.getUser().id;
    this.userService.getUserById(userId).subscribe(result => {
      this.user = result;
    });
  }

}
