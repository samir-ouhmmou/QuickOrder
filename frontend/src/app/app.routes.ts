import { Routes } from '@angular/router';
import { JwtTest } from './features/jwt-test/jwt-test';
import { Home } from './pages/home/home';
import { Carte } from './pages/carte/carte';
import { Plats } from './pages/plat/plat';
import { Restaurant } from './pages/restaurant/restaurant';
import { Login } from './pages/login/login';
import { GoogleCallback } from './pages/login/google-callback';
import { Gerant } from './pages/gerant/gerant';
import { managerGuard } from './services/auth/manager.guard';

export const routes: Routes = [
  {
    path: '',
    component: Home,
    title: 'Dar Dmana | Authentic Moroccan Catering',
  },
  {
    path: 'carte',
    component: Carte,
    title: 'Menus | Dar Dmana',
  },
  {
    path: 'plats/:categoryId',
    component: Plats,
  },
  {
    path: 'login',
    component: Login,
  },
  {
    path: 'restaurant',
    component: Restaurant,
    title: 'Reserve a Table | Dar Dmana',
  },
  {
    path: 'gerant',
    component: Gerant,
    canActivate: [managerGuard],
    title: 'Gestion du restaurant | Dar Dmana',
  },
  {
    path: 'auth/callback',
    component: GoogleCallback,
  },
  {
    path: 'jwt-test',
    component: JwtTest,
  },
  {
    path: '**',
    redirectTo: '',
  },
];
