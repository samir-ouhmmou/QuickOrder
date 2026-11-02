import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Carte } from './pages/carte/carte';
import { Restaurant } from './pages/restaurant/restaurant';

export const routes: Routes = [
  { path: '', component: Home, title: 'Dar Dmana | Authentic Moroccan Catering' },
  { path: 'carte', component: Carte, title: 'Menus | Dar Dmana' },
  { path: 'restaurant', component: Restaurant, title: 'Reserve a Table | Dar Dmana' },
  { path: '**', redirectTo: '' },
];