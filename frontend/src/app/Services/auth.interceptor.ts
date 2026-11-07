import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { API_BASE_URL } from '../core/api';
import { Auth } from './auth';

export const authInterceptor: HttpInterceptorFn = (request, next) => {
  const token = inject(Auth).getToken();

  if (!token || !request.url.startsWith(API_BASE_URL)) {
    return next(request);
  }

  return next(request.clone({ setHeaders: { Authorization: `Bearer ${token}` } }));
};
