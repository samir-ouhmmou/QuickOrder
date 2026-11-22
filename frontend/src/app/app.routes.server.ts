import { RenderMode, ServerRoute } from '@angular/ssr';

export const serverRoutes: ServerRoute[] = [
  {
    path: 'restaurant',
    renderMode: RenderMode.Client
  },
  {
    path: 'auth/callback',
    renderMode: RenderMode.Client
  },
  {
    path: 'gerant',
    renderMode: RenderMode.Client
  },
  {
    path: 'plats/:categoryId',
    renderMode: RenderMode.Server
  },
  {
    path: '**',
    renderMode: RenderMode.Prerender
  }
];
