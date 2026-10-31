import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Carte } from './pages/carte/carte';

export const routes: Routes = [
  { path: '', component: Home },
  { path: '', component: Home, title: 'Dar Dmana | Authentic Moroccan Catering' },
  { path: 'carte', component: Carte },
  { path: '**', redirectTo: '' },
];