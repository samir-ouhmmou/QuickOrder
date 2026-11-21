import { Component, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { Auth } from '../../services/auth/auth';

interface NavItem { path: string; label: string; adminOnly?: boolean }
interface NavGroup { title?: string; items: NavItem[] }

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  template: `
    <aside class="side">
      <div class="logo">Dar Dmana</div>

      @for (group of groups; track $index) {
        @if (group.title) { <div class="grp">{{ group.title }}</div> }
        @for (item of group.items; track item.path) {
          <a [routerLink]="item.path" routerLinkActive="on">{{ item.label }}</a>
        }
      }

      <div class="spacer"></div>
      <button type="button" class="logout" (click)="logout()">Se déconnecter</button>
    </aside>
  `,
  styles: [`
    .side { width: 240px; height: 100vh; position: sticky; top: 0; box-sizing: border-box;
            padding: 24px 16px; background: var(--color-sidebar);
            border-right: 1px solid var(--color-border);
            display: flex; flex-direction: column; gap: 4px; overflow-y: auto; }
    .logo { padding: 0 12px 20px; color: var(--color-primary);
            font-family: var(--font-title); font-size: 1.4rem; font-weight: 700; }
    .grp { padding: 16px 12px 6px; font-size: .7rem; font-weight: 700;
           letter-spacing: .16em; text-transform: uppercase; color: rgba(255,255,255,.6); }
    a, .logout { display: block; padding: 11px 12px; border-radius: 8px; border: 0;
                 background: none; color: rgba(255,255,255,.78); font: inherit;
                 text-align: left; text-decoration: none; cursor: pointer; }
    a:hover, .logout:hover { background: var(--color-surface); color: #fff; }
    a.on { background: var(--color-primary); color: #1a1410; font-weight: 700; }
    .spacer { flex: 1; }
    @media (max-width: 900px) { .side { display: none; } }
  `],
})
export class Sidebar {
  private auth = inject(Auth);
  private router = inject(Router);

  private isAdmin = this.auth.currentRole() === 'ADMIN';

  private all: NavGroup[] = [
    { items: [
        { path: '/admin/dashboard', label: 'Tableau de bord', adminOnly: true },
        { path: '/admin/commandes', label: 'Commandes' },
        { path: '/admin/categories', label: 'Catégories' },
        { path: '/admin/plats', label: 'Plats' },
        { path: '/admin/clients', label: 'Clients' },
        { path: '/admin/avis', label: 'Avis' },
      ]},
    { title: 'Admin', items: [
        { path: '/admin/utilisateurs', label: 'Utilisateurs', adminOnly: true },
        { path: '/admin/parametres', label: 'Paramètres', adminOnly: true },
      ]},
  ];

  groups = this.all
    .map((g) => ({ ...g, items: g.items.filter((i) => !i.adminOnly || this.isAdmin) }))
    .filter((g) => g.items.length > 0);

  logout(): void {
    this.auth.logout();
    void this.router.navigateByUrl('/login');
  }
}
