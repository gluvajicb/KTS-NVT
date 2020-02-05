import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { EventDay } from '../model/event-day';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-add-event-days',
  templateUrl: './add-event-days.component.html',
  styleUrls: ['./add-event-days.component.css']
})
export class AddEventDaysComponent implements OnInit {

  @Output() newDay: EventEmitter<EventDay>;

  eventDay: EventDay;
  dayForm: FormGroup;

  constructor(private fb: FormBuilder) {
    this.newDay = new EventEmitter();
    this.createForm();
   }

  ngOnInit() {
  }

  createForm() {
    this.dayForm = this.fb.group({
      title: ['', [Validators.required]],
      eventdate: ['', [Validators.required]]
    });
  }

  sendDay() {
    this.eventDay = this.dayForm.value;
    this.newDay.emit(this.eventDay);
    this.dayForm.reset();
  }

}
