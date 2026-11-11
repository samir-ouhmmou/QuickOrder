import { isPlatformBrowser } from '@angular/common';
import { Component, OnInit, PLATFORM_ID, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { Auth } from '../../services/auth/auth';

@Component({
  selector: 'app-google-callback',
  standalone: true,
  template: `
    <main class="callback" aria-live="polite">
      @if (error()) {
        <p role="alert">{{ error() }}</p>
        <a routerLink="/login">Retour à la connexion</a>
      } @else {
        <p>Connexion Google en cours...</p>
      }
    </main>
  `,
  styles: `
    .callback {
      display: grid;
      min-height: 100vh;
      align-content: center;
      justify-items: center;
      gap: 1rem;
      padding: 2rem;
      background: var(--color-dark);
      color: var(--color-light);
      text-align: center;
    }

    .callback a {
      color: var(--color-primary);
    }
  `,
  imports: [RouterLink],
})
export class GoogleCallback implements OnInit {
  private readonly auth = inject(Auth);
  private readonly router = inject(Router);
  private readonly platformId = inject(PLATFORM_ID);

  readonly error = signal('');

  ngOnInit(): void {
    if (!isPlatformBrowser(this.platformId)) {
      return;
    }

    const token = new URLSearchParams(window.location.hash.slice(1)).get('token');
    window.history.replaceState(null, '', window.location.pathname + window.location.search);

    if (!token) {
      this.error.set("La connexion Google n'a pas pu être finalisée.");
      return;
    }

    this.auth.completeGoogleLogin(token);
    const returnUrl = sessionStorage.getItem('quickorder_google_return_url');
    sessionStorage.removeItem('quickorder_google_return_url');
    void this.router.navigateByUrl(this.safeReturnUrl(returnUrl));
  }

  private safeReturnUrl(returnUrl: string | null): string {
    return returnUrl?.startsWith('/') &&
      !returnUrl.startsWith('//') &&
      !returnUrl.startsWith('/login') &&
      !returnUrl.startsWith('/auth/callback')
      ? returnUrl
      : '/carte';
  }
}
