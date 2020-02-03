import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddEventDaysComponent } from './add-event-days.component';

describe('AddEventDaysComponent', () => {
  let component: AddEventDaysComponent;
  let fixture: ComponentFixture<AddEventDaysComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddEventDaysComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddEventDaysComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
