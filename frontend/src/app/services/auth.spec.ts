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
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('sends only the email and password to the backend', () => {
    service.login({ email: 'client@example.com', password: 'secret123' }).subscribe();

    const request = httpMock.expectOne('http://localhost:8081/api/auth/login');
    expect(request.request.method).toBe('POST');
    expect(request.request.body).toEqual({
      email: 'client@example.com',
      password: 'secret123',
    });
    request.flush({
      token: 'token',
      type: 'Bearer',
      id: 1,
      nom: 'Client',
      prenom: 'Test',
      email: 'client@example.com',
      role: 'CLIENT',
    });
  });
});
