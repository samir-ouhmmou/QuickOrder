import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject, signal } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
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
  private readonly auth = inject(Auth);
  private readonly router = inject(Router);

  form: FormGroup;

  loading = signal(false);
  showPassword = signal(false);

  message = signal<{ ok: boolean; text: string } | null>(null);

  roles = [
    { value: 'CLIENT', label: 'Client' },
    { value: 'SERVEUR', label: 'Serveur' },
    { value: 'ADMIN', label: 'Admin' },
  ];

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      role: ['CLIENT', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      remember: [false],
    });
  }

  invalid(field: string): boolean {
    const control = this.form.get(field);

    return !!control && control.invalid && (control.dirty || control.touched);
  }

  togglePassword(): void {
    this.showPassword.update((value) => !value);
  }

  onSubmit(): void {
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
        this.message.set({ ok: true, text: 'Connexion réussie.' });
        void this.router.navigateByUrl('/');
      },
      error: (error: HttpErrorResponse) => {
        this.loading.set(false);
        this.message.set({
          ok: false,
          text:
            error.status === 0
              ? 'Le serveur est inaccessible. Vérifiez que le backend est démarré.'
              : 'Connexion impossible. Vérifiez vos identifiants et réessayez.',
        });
      },
    });
  }
}
