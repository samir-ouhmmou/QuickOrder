import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideRouter } from '@angular/router';
import { Carte } from './carte';

describe('Carte', () => {
  let component: Carte;
  let fixture: ComponentFixture<Carte>;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Carte],
      providers: [provideHttpClient(), provideHttpClientTesting(), provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(Carte);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should create', () => {
    fixture.detectChanges();
    httpMock.expectOne('http://localhost:8081/api/category').flush([]);

    expect(component).toBeTruthy();
  });

  it('loads category cards from the backend DTO', async () => {
    fixture.detectChanges();
    httpMock.expectOne('http://localhost:8081/api/category').flush([
      {
        id: 7,
        nom: 'Tajines',
        description: 'Nos tajines maison',
        active: true,
        disponible: true,
        nombresPlats: 3,
      },
    ]);
    await fixture.whenStable();
    expect(component.loading()).toBe(false);
    fixture.detectChanges();

    expect(fixture.nativeElement.textContent).toContain('Tajines');
    expect(fixture.nativeElement.textContent).toContain('3 plats');
    expect(fixture.nativeElement.textContent).toContain('Nos tajines maison');
    expect(fixture.nativeElement.querySelector('.card')?.getAttribute('href')).toBe('/plats/7?category=Tajines');
  });

  it('shows an error when the categories cannot be loaded', async () => {
    fixture.detectChanges();
    httpMock.expectOne('http://localhost:8081/api/category').error(new ProgressEvent('error'));
    await fixture.whenStable();
    expect(component.errorMessage()).toBeTruthy();
    fixture.detectChanges();

    expect(fixture.nativeElement.querySelector('[role="alert"]')).toBeTruthy();
  });
});
