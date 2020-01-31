import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserEventContainerComponent } from './user-event-container.component';

describe('UserEventContainerComponent', () => {
  let component: UserEventContainerComponent;
  let fixture: ComponentFixture<UserEventContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserEventContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserEventContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
