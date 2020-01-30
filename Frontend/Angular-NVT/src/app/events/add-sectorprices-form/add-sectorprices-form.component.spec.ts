import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddSectorpricesFormComponent } from './add-sectorprices-form.component';

describe('AddSectorpricesFormComponent', () => {
  let component: AddSectorpricesFormComponent;
  let fixture: ComponentFixture<AddSectorpricesFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddSectorpricesFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddSectorpricesFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
