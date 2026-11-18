import { ComponentFixture, TestBed } from '@angular/core/testing';
import { JwtTest } from './jwt-test';

describe('JwtTest', () => {
  let component: JwtTest;
  let fixture: ComponentFixture<JwtTest>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [JwtTest],
    }).compileComponents();

    fixture = TestBed.createComponent(JwtTest);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
