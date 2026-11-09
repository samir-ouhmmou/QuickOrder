import { Routes } from '@angular/router';

import { Home } from './pages/home/home';
import { Carte } from './pages/carte/carte';
import { Plats } from './pages/plat/plat';
import { Login } from './pages/login/login';
import { GoogleCallback } from './pages/login/google-callback';

export const routes: Routes = [
  {
    path: '',
    component: Home,
    title: 'Dar Dmana | Authentic Moroccan Catering',
  },
  {
    path: 'carte',
    component: Carte,
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
    path: 'auth/callback',
    component: GoogleCallback,
  },
  {
    path: '**',
    redirectTo: '',
  },
];
