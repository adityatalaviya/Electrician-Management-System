import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const workerAuthGuard: CanActivateFn = () => {

  const router = inject(Router);

  const token = localStorage.getItem('workerToken');

  if (token) return true;

  router.navigate(['/worker/login']);
  return false;
};