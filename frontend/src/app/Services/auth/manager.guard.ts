import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Auth } from './auth';

export const managerGuard: CanActivateFn = (_route, state) => {
  const auth = inject(Auth);
  const router = inject(Router);

  if (auth.isManager()) {
    return true;
  }

  if (!auth.getToken()) {
    return router.createUrlTree(['/login'], {
      queryParams: { returnUrl: state.url },
    });
  }

  return router.createUrlTree(['/login'], {
    queryParams: { accessDenied: 'true' },
  });
};
