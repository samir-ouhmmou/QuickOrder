import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { Restaurant } from './restaurant';

describe('Restaurant', () => {
  let fixture: ComponentFixture<Restaurant>;
  let httpMock: HttpTestingController;

  const categories = [
    {
      id: 4,
      nom: 'Tajines',
      description: null,
      active: true,
      disponible: true,
      nombresPlats: 2,
    },
  ];

  beforeEach(async () => {
    localStorage.clear();
    sessionStorage.clear();

    await TestBed.configureTestingModule({
      imports: [Restaurant],
      providers: [provideHttpClient(), provideHttpClientTesting(), provideRouter([])],
    }).compileComponents();

    fixture = TestBed.createComponent(Restaurant);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('shows only available dishes and updates the cart quantities and total', async () => {
    fixture.detectChanges();
    httpMock.expectOne('http://localhost:8081/api/category').flush(categories);
    httpMock.expectOne('http://localhost:8081/api/plats?categoryId=4').flush([
      { id: 12, nom: 'Tajine disponible', description: null, prix: 35, disponible: true },
      { id: 13, nom: 'Tajine indisponible', description: null, prix: 50, disponible: false },
    ]);
    await fixture.whenStable();
    fixture.detectChanges();

    expect(fixture.nativeElement.textContent).toContain('Tajine disponible');
    expect(fixture.nativeElement.textContent).not.toContain('Tajine indisponible');

    const plat = fixture.componentInstance.categories()[0].plats[0];
    fixture.componentInstance.ajouter(plat);
    fixture.componentInstance.ajouter(plat);
    expect(fixture.componentInstance.quantite(plat.id)).toBe(2);
    expect(fixture.componentInstance.total()).toBe(70);

    fixture.componentInstance.diminuer(plat.id);
    expect(fixture.componentInstance.quantite(plat.id)).toBe(1);
    expect(fixture.componentInstance.total()).toBe(35);
  });

  it('sends dish ids and quantities, then displays the creation confirmation', async () => {
    sessionStorage.setItem('quickorder_token', 'client-token');
    fixture.detectChanges();
    httpMock.expectOne('http://localhost:8081/api/category').flush(categories);
    httpMock.expectOne('http://localhost:8081/api/table/disponibles').flush([
      { id: 3, numero: 3, capacite: 4 },
    ]);
    httpMock.expectOne('http://localhost:8081/api/plats?categoryId=4').flush([
      { id: 12, nom: 'Tajine disponible', description: null, prix: 35, disponible: true },
    ]);
    await fixture.whenStable();

    const component = fixture.componentInstance;
    component.ajouter(component.categories()[0].plats[0]);
    component.ajouter(component.categories()[0].plats[0]);
    component.tableSelectionnee.set(3);
    component.creerCommande();

    const request = httpMock.expectOne('http://localhost:8081/api/commande');
    expect(request.request.method).toBe('POST');
    expect(request.request.body).toEqual({
      commentaire: null,
      tableRestaurant: { id: 3 },
      ligneCommandes: [{ quantite: 2, plat: { id: 12 } }],
    });
    request.flush({ id: 42, dateCreation: '2026-11-18T12:00:00', etat: 'EN_ATTENTE', montantTotal: 70 });
    fixture.detectChanges();

    expect(component.confirmation()?.id).toBe(42);
    expect(component.panier()).toEqual([]);
    expect(fixture.nativeElement.textContent).toContain('Commande n°42 enregistrée.');
  });

  it('shows backend errors when order creation fails', async () => {
    sessionStorage.setItem('quickorder_token', 'client-token');
    fixture.detectChanges();
    httpMock.expectOne('http://localhost:8081/api/category').flush(categories);
    httpMock.expectOne('http://localhost:8081/api/table/disponibles').flush([]);
    httpMock.expectOne('http://localhost:8081/api/plats?categoryId=4').flush([
      { id: 12, nom: 'Tajine disponible', description: null, prix: 35, disponible: true },
    ]);
    await fixture.whenStable();

    const component = fixture.componentInstance;
    component.ajouter(component.categories()[0].plats[0]);
    component.creerCommande();
    httpMock
      .expectOne('http://localhost:8081/api/commande')
      .flush({ detail: 'Le plat n’est plus disponible.' }, { status: 409, statusText: 'Conflict' });
    fixture.detectChanges();

    expect(component.erreurCommande()).toBe('Le plat n’est plus disponible.');
    expect(fixture.nativeElement.textContent).toContain('Le plat n’est plus disponible.');
  });
});
