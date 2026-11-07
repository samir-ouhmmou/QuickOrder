import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { Auth } from './auth';

describe('Auth', () => {
  let service: Auth;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    service = TestBed.inject(Auth);
    httpMock = TestBed.inject(HttpTestingController);
    localStorage.clear();
    sessionStorage.clear();
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('sends credentials to the backend and stores the token for the session', () => {
    service.login('client@example.com', 'password123', false).subscribe();

    const request = httpMock.expectOne('http://localhost:8081/api/auth/login');
    expect(request.request.method).toBe('POST');
    expect(request.request.body).toEqual({ email: 'client@example.com', password: 'password123' });
    request.flush({
      token: 'access-token',
      type: 'Bearer',
      id: 1,
      nom: 'Client',
      prenom: 'Test',
      email: 'client@example.com',
      role: 'CLIENT',
    });

    expect(sessionStorage.getItem('quickorder_token')).toBe('access-token');
    expect(localStorage.getItem('quickorder_token')).toBeNull();
  });

  it('stores the token persistently when remember is selected', () => {
    service.login('client@example.com', 'password123', true).subscribe();
    httpMock.expectOne('http://localhost:8081/api/auth/login').flush({
      token: 'access-token',
      type: 'Bearer',
      id: 1,
      nom: 'Client',
      prenom: 'Test',
      email: 'client@example.com',
      role: 'CLIENT',
    });

    expect(localStorage.getItem('quickorder_token')).toBe('access-token');
    expect(sessionStorage.getItem('quickorder_token')).toBeNull();
  });
});
