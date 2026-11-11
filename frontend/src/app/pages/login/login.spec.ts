import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, convertToParamMap, provideRouter, Router } from '@angular/router';
import { vi } from 'vitest';
import { Login } from './login';

describe('Login', () => {
  let component: Login;
  let fixture: ComponentFixture<Login>;
  let httpMock: HttpTestingController;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Login],
      providers: [
        provideHttpClient(),
        provideHttpClientTesting(),
        provideRouter([]),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              queryParamMap: convertToParamMap({ returnUrl: '/plats/7?category=Tajines' }),
            },
          },
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(Login);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);
    router = TestBed.inject(Router);
    fixture.detectChanges();
    httpMock.expectOne('http://localhost:8081/api/auth/google/enabled').flush(false);
    await fixture.whenStable();
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('shows Google sign-in button', () => {
    fixture.detectChanges();

    expect(fixture.nativeElement.textContent).toContain('Continuer avec Google');
  });

  it('displays a friendly message when Google sign-in is clicked without server configuration', () => {
    component.startGoogleLogin();

    expect(component.message()).toContain("La connexion Google n'est pas encore configurée");
  });

  it('returns to the requested page after a successful login', () => {
    vi.spyOn(router, 'navigateByUrl').mockResolvedValue(true);
    component.form.setValue({
      email: 'client@example.com',
      password: 'password123',
      remember: false,
    });

    component.submit();

    httpMock.expectOne('http://localhost:8081/api/auth/login').flush({
      token: 'access-token',
      type: 'Bearer',
      id: 1,
      nom: 'Client',
      prenom: 'Test',
      email: 'client@example.com',
      role: 'CLIENT',
    });

    expect(router.navigateByUrl).toHaveBeenCalledWith('/plats/7?category=Tajines');
  });
});
