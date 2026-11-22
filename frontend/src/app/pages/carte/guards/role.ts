import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import {Auth,AppRole} from '../../../services/auth/auth';

export const roleGuard =
  (...allowed: AppRole[]): CanActivateFn =>
  (_route, state) => {
    const auth = inject(Auth);
    const router = inject(Router);

    if (!auth.getToken()) {
      return router.createUrlTree(['/login'], { queryParams: { returnUrl: state.url } });
    }
    return allowed.includes(auth.currentRole()) ? true : router.createUrlTree([auth.homeUrl()]);
  };
