import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CanvasEventPricesComponent } from './user-canvas-event.component';

describe('CanvasUserEventComponent', () => {
  let component: CanvasEventPricesComponent;
  let fixture: ComponentFixture<CanvasUserEventComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CanvasUserEventComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CanvasUserEventComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
