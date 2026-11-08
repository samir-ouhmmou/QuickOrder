import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { Auth } from '../../services/auth/auth';

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

  readonly form = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    remember: [false],
  });

  readonly loading = signal(false);
  readonly showPassword = signal(false);
  readonly message = signal<string | null>(null);

  invalid(field: 'email' | 'password'): boolean {
    const control = this.form.controls[field];
    return control.invalid && (control.dirty || control.touched);
  }

  togglePassword(): void {
    this.showPassword.update((value) => !value);
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
        void this.router.navigateByUrl('/carte');
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
}
