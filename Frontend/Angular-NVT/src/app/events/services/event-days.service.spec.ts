import { TestBed } from '@angular/core/testing';

import { EventDaysService } from './event-days.service';

describe('EventDaysService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: EventDaysService = TestBed.get(EventDaysService);
    expect(service).toBeTruthy();
  });
});
