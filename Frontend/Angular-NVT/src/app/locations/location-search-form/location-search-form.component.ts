import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { SearchLocation } from '../model/search-location';

@Component({
  selector: 'app-location-search-form',
  templateUrl: './location-search-form.component.html',
  styleUrls: ['./location-search-form.component.css']
})
export class LocationSearchFormComponent implements OnInit {

  @Output() searchDataEvent: EventEmitter<SearchLocation>;
  searchForm: FormGroup;
  searchLocation: SearchLocation;

  constructor(private fb: FormBuilder) {
    this.searchDataEvent = new EventEmitter();
    this.createForm();
   }

  ngOnInit() {
  }

  createForm() {
    this.searchForm = this.fb.group({
      title: [''],
      address: ['']
    });
  }

  sendSearchData() {
    this.searchLocation = this.searchForm.value;
    this.searchDataEvent.emit(this.searchLocation);
    this.searchForm.reset();
  }

}
