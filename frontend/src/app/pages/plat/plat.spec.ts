import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, convertToParamMap, provideRouter, Router } from '@angular/router';
import { of } from 'rxjs';
import { vi } from 'vitest';
import { Plats } from './plat';

describe('Plats', () => {
  let fixture: ComponentFixture<Plats>;
  let httpMock: HttpTestingController;
  let router: Router;

  beforeEach(async () => {
    sessionStorage.clear();
    localStorage.clear();
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
    router = TestBed.inject(Router);
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
    expect(fixture.nativeElement.querySelector('.dish-card img').getAttribute('src')).toBe(
      'images/plats/tajine.png',
    );
    expect(fixture.nativeElement.querySelector('.dish-card img').getAttribute('alt')).toBe(
      'Tajine de poulet',
    );
  });

  it('selects a matching dish image and uses a fallback for other dishes', () => {
    expect(fixture.componentInstance.imageFor('Couscous marocain')).toBe(
      'images/plats/conscous.png',
    );
    expect(fixture.componentInstance.imageFor('Thé à la menthe')).toBe(
      'images/plats/na3na3.png',
    );
    expect(fixture.componentInstance.imageFor('Briouates au fromage')).toBe(
      'images/plats/pastilla.png',
    );
    expect(fixture.componentInstance.imageFor('Salade marocaine')).toBe('images/hero.jpg');
  });

  it('adds an available dish to the cart and updates its count', async () => {
    sessionStorage.setItem('quickorder_token', 'test-token');
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

  it('requires login before adding a dish and returns to the category after login', async () => {
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
    vi.spyOn(router, 'navigate').mockResolvedValue(true);

    fixture.nativeElement.querySelector('.btn-ajout').click();

    expect(router.navigate).toHaveBeenCalledWith(['/login'], {
      queryParams: { returnUrl: router.url },
    });
    expect(fixture.componentInstance.panier()).toHaveLength(0);
  });
});
