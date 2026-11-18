import { TestBed } from '@angular/core/testing';
import { JwtTestService } from './jwt-test.service';

describe('JwtTestService', () => {
  let service: JwtTestService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JwtTestService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
