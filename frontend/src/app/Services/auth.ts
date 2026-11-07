import { HttpClient } from '@angular/common/http';
import { isPlatformBrowser } from '@angular/common';
import { inject, Injectable, PLATFORM_ID } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { API_BASE_URL } from '../core/api';

export interface LoginResponse {
  token: string;
  type: string;
  id: number;
  nom: string;
  prenom: string;
  email: string;
  role: string;
}

@Injectable({ providedIn: 'root' })
export class Auth {
  private readonly http = inject(HttpClient);
  private readonly platformId = inject(PLATFORM_ID);

  login(email: string, password: string, remember: boolean): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>(`${API_BASE_URL}/auth/login`, { email, password })
      .pipe(tap((response) => this.saveToken(response.token, remember)));
  }

  getToken(): string | null {
    if (!isPlatformBrowser(this.platformId)) {
      return null;
    }

    return localStorage.getItem('quickorder_token') ?? sessionStorage.getItem('quickorder_token');
  }

  logout(): void {
    if (!isPlatformBrowser(this.platformId)) {
      return;
    }

    localStorage.removeItem('quickorder_token');
    sessionStorage.removeItem('quickorder_token');
  }

  private saveToken(token: string, remember: boolean): void {
    if (!isPlatformBrowser(this.platformId)) {
      return;
    }

    this.logout();
    const storage = remember ? localStorage : sessionStorage;
    storage.setItem('quickorder_token', token);
  }
}
