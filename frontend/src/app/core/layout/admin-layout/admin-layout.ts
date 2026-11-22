import { Component, inject, signal } from '@angular/core';
import { Router, RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { Auth } from '../../../services/auth/auth';

interface NavItem {
  label: string;
  path: string;
  adminOnly?: boolean;
}

@Component({
  selector: 'app-admin-layout',
  standalone: true,
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './admin-layout.html',
  styleUrl: './admin-layout.css',
})
export class AdminLayout {
  private readonly auth = inject(Auth);
  private readonly router = inject(Router);

  readonly name = this.auth.currentName();
  readonly isAdmin = this.auth.currentRole() === 'ADMIN';
  readonly menuOpen = signal(false); // pour mobile

  // Ajoute une ligne ici quand tu crées une nouvelle page
  readonly items: NavItem[] = [
    { label: 'Tableau de bord', path: '/admin/dashboard' },
    { label: 'Commandes', path: '/admin/commandes' },
    // { label: 'Catégories', path: '/admin/categories' },
    // { label: 'Plats', path: '/admin/plats' },
    // { label: 'Clients', path: '/admin/clients' },
    // { label: 'Avis', path: '/admin/avis' },
    // { label: 'Utilisateurs', path: '/admin/utilisateurs', adminOnly: true },
    // { label: 'Paramètres', path: '/admin/parametres', adminOnly: true },
  ];

  get visibleItems(): NavItem[] {
    return this.items.filter((i) => !i.adminOnly || this.isAdmin);
  }

  toggleMenu(): void {
    this.menuOpen.update((v) => !v);
  }

  logout(): void {
    this.auth.logout();
    void this.router.navigateByUrl('/login');
  }
}
