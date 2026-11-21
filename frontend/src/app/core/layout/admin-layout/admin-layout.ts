import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Sidebar } from '../../../shared/sidebar/sidebar';
import { Auth } from '../../../services/auth/auth';

@Component({
  selector: 'app-admin-layout',
  standalone: true,
  imports: [RouterOutlet, Sidebar],
  templateUrl: './admin-layout.html',
  styleUrl: './admin-layout.css',
})
export class AdminLayout {
  private auth = inject(Auth);
  name = this.auth.currentName();
  initial = this.name.charAt(0).toUpperCase();
  roleLabel = { ADMIN: 'Administrateur', GERANT: 'Gérant', CLIENT: 'Client' }[
    this.auth.currentRole()
  ];
}
