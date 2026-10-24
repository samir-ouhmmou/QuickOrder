import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { Welcome } from './pages/welcome/welcome';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'carte', component: Welcome },
  { path: '**', redirectTo: '' },
];