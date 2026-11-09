import { isPlatformBrowser } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, PLATFORM_ID, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Auth } from '../../services/auth/auth';
import { API_BASE_URL } from '../../core/api';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  private readonly fb = inject(FormBuilder);
  private readonly auth = inject(Auth);
  private readonly router = inject(Router);
  private readonly route = inject(ActivatedRoute);
  private readonly platformId = inject(PLATFORM_ID);
  readonly requiresLoginForCart = this.route.snapshot.queryParamMap.has('returnUrl');
  readonly googleLoginError = this.route.snapshot.queryParamMap.has('googleError');

  readonly form = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    remember: [false],
  });

  readonly loading = signal(false);
  readonly googleEnabled = signal(false);
  readonly googleStatusLoading = signal(true);
  readonly googleStatusError = signal(false);
  readonly showPassword = signal(false);
  readonly message = signal<string | null>(null);

  ngOnInit(): void {
    if (!isPlatformBrowser(this.platformId)) {
      return;
    }

    this.auth.googleLoginEnabled().subscribe({
      next: (enabled) => {
        this.googleEnabled.set(enabled);
        this.googleStatusLoading.set(false);
      },
      error: (error: HttpErrorResponse) => {
        console.error('Impossible de vérifier la disponibilité de Google Login.', error);
        this.googleStatusError.set(true);
        this.googleStatusLoading.set(false);
      },
    });
  }

  invalid(field: 'email' | 'password'): boolean {
    const control = this.form.controls[field];
    return control.invalid && (control.dirty || control.touched);
  }

  togglePassword(): void {
    this.showPassword.update((value) => !value);
  }

  startGoogleLogin(): void {
    if (!isPlatformBrowser(this.platformId)) {
      return;
    }

    if (!this.googleEnabled()) {
      this.message.set(
        "La connexion Google n'est pas encore configurée sur le serveur. Vous pouvez vous connecter avec votre email ci-dessous.",
      );
      return;
    }

    sessionStorage.setItem('quickorder_google_return_url', this.returnUrl());
    const backendUrl = API_BASE_URL.replace(/\/api\/?$/, '');
    window.location.assign(`${backendUrl}/oauth2/authorization/google`);
  }

  submit(): void {
    this.message.set(null);

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const { email, password, remember } = this.form.getRawValue();
    this.loading.set(true);
    this.auth.login(email, password, remember).subscribe({
      next: () => {
        this.loading.set(false);
        void this.router.navigateByUrl(this.returnUrl());
      },
      error: (error: HttpErrorResponse) => {
        this.loading.set(false);
        this.message.set(
          error.status === 0
            ? 'Impossible de joindre le serveur. Vérifiez que le backend est démarré.'
            : error.status === 401 || error.status === 403
              ? 'Email ou mot de passe incorrect.'
              : 'La connexion est momentanément indisponible. Réessayez plus tard.',
        );
      },
    });
  }

  private returnUrl(): string {
    const returnUrl = this.route.snapshot.queryParamMap.get('returnUrl');

    return returnUrl?.startsWith('/') && !returnUrl.startsWith('//') && !returnUrl.startsWith('/login')
      ? returnUrl
      : '/carte';
  }
}
