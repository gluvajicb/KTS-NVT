import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SectorCanvasComponent } from './sector-canvas.component';

describe('SectorCanvasComponent', () => {
  let component: SectorCanvasComponent;
  let fixture: ComponentFixture<SectorCanvasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SectorCanvasComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SectorCanvasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
