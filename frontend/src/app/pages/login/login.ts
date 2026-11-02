import { Component, signal } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule
} from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {

  form: FormGroup;

  loading = signal(false);
  showPassword = signal(false);

  message = signal<{ ok: boolean; text: string } | null>(null);

  roles = [
    { value: 'CLIENT', label: 'Client' },
    { value: 'SERVEUR', label: 'Serveur' },
    { value: 'ADMIN', label: 'Admin' }
  ];

  constructor(private fb: FormBuilder) {

    this.form = this.fb.group({
      role: ['CLIENT', Validators.required],

      email: ['', [
        Validators.required,
        Validators.email
      ]],

      password: ['', [
        Validators.required,
        Validators.minLength(6)
      ]],

      remember: [false]
    });

  }

  invalid(field: string): boolean {
    const control = this.form.get(field);

    return !!control &&
      control.invalid &&
      (control.dirty || control.touched);
  }

  togglePassword(): void {
    this.showPassword.update(value => !value);
  }

  submit(): void {

    this.message.set(null);

    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    console.log('Données du formulaire :', this.form.value);

    // La connexion à Spring Boot sera ajoutée ici.
  }
}
