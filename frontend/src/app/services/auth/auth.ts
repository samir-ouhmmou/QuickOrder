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

export type AppRole = 'ADMIN' | 'GERANT' | 'CLIENT';

const TOKEN_KEY = 'quickorder_token';
const ROLE_KEY = 'quickorder_role';
const NAME_KEY = 'quickorder_name';

@Injectable({ providedIn: 'root' })
export class Auth {
  private readonly http = inject(HttpClient);
  private readonly platformId = inject(PLATFORM_ID);

  login(email: string, password: string, remember: boolean): Observable<LoginResponse> {
    return this.http
      .post<LoginResponse>(`${API_BASE_URL}/auth/login`, { email, password })
      .pipe(tap((r) => this.saveSession(r.token, remember, r.role, `${r.prenom} ${r.nom}`.trim())));
  }

  googleLoginEnabled(): Observable<boolean> {
    return this.http.get<boolean>(`${API_BASE_URL}/auth/google/enabled`);
  }

  completeGoogleLogin(token: string): void {
    this.saveSession(token, false, null, null);
  }

  getToken(): string | null {
    if (!isPlatformBrowser(this.platformId)) return null;
    return localStorage.getItem(TOKEN_KEY) ?? sessionStorage.getItem(TOKEN_KEY);
  }

  /** Rôle normalisé : ADMIN, GERANT ou CLIENT. */
  currentRole(): AppRole {
    if (!isPlatformBrowser(this.platformId) || !this.getToken()) return 'CLIENT';

    const stored = localStorage.getItem(ROLE_KEY) ?? sessionStorage.getItem(ROLE_KEY);
    return this.normalizeRole(stored ?? this.roleFromToken());
  }

  /** Nom affiché dans la topbar. */
  currentName(): string {
    if (!isPlatformBrowser(this.platformId)) return 'Admin';
    return localStorage.getItem(NAME_KEY) ?? sessionStorage.getItem(NAME_KEY) ?? 'Admin';
  }

  /** Page d'accueil selon le rôle. */
  homeUrl(): string {
    switch (this.currentRole()) {
      case 'ADMIN':
        return '/admin/dashboard';
      case 'GERANT':
        return '/gerant';
      default:
        return '/carte';
    }
  }

  isStaff(): boolean {
    const role = this.currentRole();
    return role === 'ADMIN' || role === 'GERANT';
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
    if (!isPlatformBrowser(this.platformId)) return;
    for (const key of [TOKEN_KEY, ROLE_KEY, NAME_KEY]) {
      localStorage.removeItem(key);
      sessionStorage.removeItem(key);
    }
  }

  private saveSession(
    token: string,
    remember: boolean,
    role: string | null,
    name: string | null,
  ): void {
    if (!isPlatformBrowser(this.platformId)) return;

    this.logout();
    const storage = remember ? localStorage : sessionStorage;
    storage.setItem(TOKEN_KEY, token);
    if (role) storage.setItem(ROLE_KEY, role);
    if (name) storage.setItem(NAME_KEY, name);
  }

  // Cas Google : on lit le rôle dans le payload du JWT
  private roleFromToken(): string | null {
    try {
      const payload = this.getToken()!.split('.')[1].replace(/-/g, '+').replace(/_/g, '/');
      const claims = JSON.parse(decodeURIComponent(escape(atob(payload))));
      const raw = claims.role ?? claims.roles ?? claims.authorities;
      const first = Array.isArray(raw) ? raw[0] : raw;
      return first?.authority ?? first ?? null;
    } catch {
      return null;
    }
  }

  // Accepte "ADMIN", "ROLE_ADMIN", "admin", "Gérant", etc.
  private normalizeRole(value: string | null): AppRole {
    const role = (value ?? '')
      .toUpperCase()
      .replace('ROLE_', '')
      .normalize('NFD')
      .replace(/[\u0300-\u036f]/g, '');
    if (role === 'ADMIN') return 'ADMIN';
    if (role === 'GERANT' || role === 'MANAGER') return 'GERANT';
    return 'CLIENT';
  }
}
