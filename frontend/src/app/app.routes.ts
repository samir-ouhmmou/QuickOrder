import { Routes } from '@angular/router';
import { JwtTest } from './features/jwt-test/jwt-test';
import { Home } from './pages/home/home';
import { Carte } from './pages/carte/carte';
import { Plats } from './pages/plat/plat';
import { Login } from './pages/login/login';
import { GoogleCallback } from './pages/login/google-callback';
import { Restaurant } from './pages/restaurant/restaurant';
import {roleGuard} from './pages/carte/guards/role';

export const routes: Routes = [
  { path: '', component: Home, title: 'Dar Dmana | Authentic Moroccan Catering' },
  { path: 'carte', component: Carte },
  { path: 'plats/:categoryId', component: Plats },
  { path: 'login', component: Login },
  { path: 'restaurant', component: Restaurant },
  { path: 'auth/callback', component: GoogleCallback },
  { path: 'jwt-test', component: JwtTest },

  {
    path: 'admin',
    canActivate: [roleGuard('ADMIN', 'GERANT')],
    loadComponent: () =>
      import('./core/layout/admin-layout/admin-layout').then((m) => m.AdminLayout),
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
      {
        path: 'dashboard',
        title: 'Tableau de bord | Dar Dmana',
        canActivate: [roleGuard('ADMIN')],
        loadComponent: () =>
          import('./features/admin/dashboard/dashboard').then((m) => m.Dashboard),
      },
      {
        //path: 'commandes',
        //title: 'Commandes | Dar Dmana',
        //loadComponent: () =>
          //import('./features/admin/commandes/commandes').then((m) => m.Commandes),
      },
      {
     //   path: 'categories',
      //  title: 'Catégories | Dar Dmana',
       // loadComponent: () =>
        //  import('./features/admin/categories/categories').then((m) => m.Categories),
      },
      {
      //  path: 'plats',
      //  title: 'Plats | Dar Dmana',
        //loadComponent: () => import('./features/admin/plats/plats').then((m) => m.Plats),
      },
      {
      //  path: 'clients',
        //title: 'Clients | Dar Dmana',
        //loadComponent: () => import('./features/admin/clients/clients').then((m) => m.Clients),
      },
      {
       // path: 'avis',
       // title: 'Avis | Dar Dmana',
        //loadComponent: () => import('./features/admin/avis/avis').then((m) => m.Avis),
      },
      {
        //path: 'utilisateurs',
        //title: 'Utilisateurs | Dar Dmana',
        //canActivate: [roleGuard('ADMIN')],
        //loadComponent: () =>
          //import('./features/admin/utilisateurs/utilisateurs').then((m) => m.Utilisateurs),
      },
      {
       // path: 'parametres',
       // title: 'Paramètres | Dar Dmana',
       // canActivate: [roleGuard('ADMIN')],
       // loadComponent: () =>
         // import('./features/admin/parametres/parametres').then((m) => m.Parametres),
      },
    ],
  },

  { path: '**', redirectTo: '' },
];
