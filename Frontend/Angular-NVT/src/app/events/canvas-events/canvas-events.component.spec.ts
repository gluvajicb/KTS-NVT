import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CanvasEventsComponent } from './canvas-events.component';

describe('CanvasEventsComponent', () => {
  let component: CanvasEventsComponent;
  let fixture: ComponentFixture<CanvasEventsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CanvasEventsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CanvasEventsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
