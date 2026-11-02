import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, convertToParamMap, provideRouter } from '@angular/router';
import { of } from 'rxjs';
import { Plats } from './plat';

describe('Plats', () => {
  let fixture: ComponentFixture<Plats>;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Plats],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([]),
        {
          provide: ActivatedRoute,
          useValue: {
            paramMap: of(convertToParamMap({ categoryId: '7' })),
            snapshot: {
              queryParamMap: convertToParamMap({ category: 'Tajines' }),
            },
          },
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(Plats);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('loads and displays plats for the selected category', async () => {
    fixture.detectChanges();
    httpMock.expectOne('http://localhost:8081/api/plats?categoryId=7').flush([
      {
        id: 12,
        nom: 'Tajine de poulet',
        description: 'Poulet aux olives',
        prix: 85,
        disponible: true,
      },
    ]);
    await fixture.whenStable();
    fixture.detectChanges();

    expect(fixture.nativeElement.textContent).toContain('Tajines');
    expect(fixture.nativeElement.textContent).toContain('Tajine de poulet');
    expect(fixture.nativeElement.textContent).toContain('85 MAD');
  });

  it('adds an available dish to the cart and updates its count', async () => {
    fixture.detectChanges();
    httpMock.expectOne('http://localhost:8081/api/plats?categoryId=7').flush([
      {
        id: 12,
        nom: 'Tajine de poulet',
        description: 'Poulet aux olives',
        prix: 85,
        disponible: true,
      },
    ]);
    await fixture.whenStable();
    fixture.detectChanges();

    fixture.nativeElement.querySelector('.btn-ajout').click();
    fixture.detectChanges();

    expect(fixture.nativeElement.querySelector('.badge').textContent.trim()).toBe('1');
    expect(fixture.componentInstance.panier()).toHaveLength(1);
  });
});
