import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CanvasEventPricesComponent } from './canvas-event-prices.component';

describe('CanvasEventPricesComponent', () => {
  let component: CanvasEventPricesComponent;
  let fixture: ComponentFixture<CanvasEventPricesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CanvasEventPricesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CanvasEventPricesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
