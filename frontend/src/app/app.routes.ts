import { Routes } from '@angular/router';
import { Restaurant } from './pages/restaurant/restaurant';

export const routes: Routes = [
  { path: '', component: Restaurant },
  { path: '**', redirectTo: '' },
];