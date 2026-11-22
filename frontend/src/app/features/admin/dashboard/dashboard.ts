import { Component, inject, signal } from '@angular/core';
import { CurrencyPipe } from '@angular/common';
import {AdminDashboardService} from '../../../services/admin/admin-dashboard.service';
import { DashboardStats } from '../../../core/Model/dashboard-stats';
import { StatCard } from '../../../shared/stat-card/stat-card';
import { PageHeader } from '../../../shared/page-header/page-header';
import { Auth } from '../../../services/auth/auth';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [StatCard, PageHeader, CurrencyPipe],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {
  private readonly service = inject(AdminDashboardService);
  readonly firstName = inject(Auth).currentName().split(' ')[0];

  readonly stats = signal<DashboardStats | null>(null);
  readonly loading = signal(true);
  readonly error = signal(false);

  // Données d'exemple, à remplacer par l'API plus tard
  readonly chart = [65, 85, 55, 105, 125, 95, 75, 115, 135, 100, 145, 120, 90];
  readonly topPlats = [
    { name: 'Tajine d’agneau', pct: 90 },
    { name: 'Couscous royal', pct: 76 },
    { name: 'Pastilla', pct: 58 },
    { name: 'Harira', pct: 44 },
    { name: 'Thé et pâtisseries', pct: 30 },
  ];
  readonly commandes = [
    { client: '[Client]', date: 'Aujourd’hui, 19:30', total: 240, statut: 'En attente' },
    { client: '[Client]', date: 'Aujourd’hui, 20:00', total: 180, statut: 'Confirmée' },
    { client: '[Client]', date: 'Demain, 13:00', total: 950, statut: 'Confirmée' },
  ];

  constructor() {
    this.loadStats();
  }

  loadStats(): void {
    this.loading.set(true);
    this.error.set(false);

    this.service.getStats().subscribe({
      next: (s) => {
        this.stats.set(s);
        this.loading.set(false);
      },
      error: () => {
        this.error.set(true);
        this.loading.set(false);
      },
    });
  }

  statusClass(s: string): string {
    return s === 'Confirmée' ? 'ok' : s === 'En attente' ? 'wait' : 'off';
  }
}
