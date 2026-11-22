import { Routes } from '@angular/router';
import { JwtTest } from './features/jwt-test/jwt-test';
import { Home } from './pages/home/home';
import { Carte } from './pages/carte/carte';
import { Plats } from './pages/plat/plat';
import { Login } from './pages/login/login';
import { GoogleCallback } from './pages/login/google-callback';
import { Restaurant } from './pages/restaurant/restaurant';
import { roleGuard } from './pages/carte/guards/role';
import { Dashboard } from './features/admin/dashboard/dashboard';
import { AdminLayout } from './core/layout/admin-layout/admin-layout';
import { Gerant } from './pages/gerant/gerant';
import { managerGuard } from './services/auth/manager.guard';

export const routes: Routes = [
  { path: '', component: Home, title: 'Dar Dmana | Authentic Moroccan Catering' },
  { path: 'carte', component: Carte },
  { path: 'plats/:categoryId', component: Plats },
  { path: 'login', component: Login },
  { path: 'restaurant', component: Restaurant },
  {
    path: 'gerant',
    component: Gerant,
    canActivate: [managerGuard],
    title: 'Gestion du restaurant | Dar Dmana',
  },
  { path: 'auth/callback', component: GoogleCallback },
  { path: 'jwt-test', component: JwtTest },

  {
    path: 'admin',
    component: AdminLayout,
    canActivate: [roleGuard('ADMIN')],
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
      {
        path: 'dashboard',
        title: 'Tableau de bord | Dar Dmana',
        component: Dashboard,
      },
      // les autres pages ici
    ],
  },

  { path: '**', redirectTo: '' },
];
