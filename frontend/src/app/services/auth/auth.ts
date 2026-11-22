import { HttpClient } from '@angular/common/http';
import { isPlatformBrowser } from '@angular/common';
import { inject, Injectable, PLATFORM_ID } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { API_BASE_URL } from '../../core/api';

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

  googleLoginEnabled(): Observable<boolean> {
    return this.http.get<boolean>(`${API_BASE_URL}/auth/google/enabled`);
  }

  completeGoogleLogin(token: string): void {
    this.saveToken(token, false);
  }

  getToken(): string | null {
    if (!isPlatformBrowser(this.platformId)) {
      return null;
    }

    return localStorage.getItem('quickorder_token') ?? sessionStorage.getItem('quickorder_token');
  }

  getRole(): string | null {
    const token = this.getToken();
    if (!token) {
      return null;
    }

    try {
      const payload = token.split('.')[1];
      if (!payload) {
        return null;
      }

      const claims: unknown = JSON.parse(atob(payload.replace(/-/g, '+').replace(/_/g, '/')));
      if (typeof claims === 'object' && claims !== null && 'role' in claims) {
        const role = claims.role;
        return typeof role === 'string' ? role.toUpperCase() : null;
      }
    } catch {
      return null;
    }

    return null;
  }

  isManager(): boolean {
    return ['ADMIN', 'GERANT'].includes(this.getRole() ?? '');
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
