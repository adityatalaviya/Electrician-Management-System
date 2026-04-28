import { Routes } from '@angular/router';
import { AdminGuard } from './core/guards/admin.guard';
import { ElectricianGuard } from './core/guards/electrician.guard';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./home/home').then(m => m.Home)
  },
  {
    path: 'admin/login',
    loadComponent: () =>
      import('./admin/login/login').then(m => m.AdminLogin)
  },
  {
    path: 'admin',
    canActivate: [AdminGuard],
    loadChildren: () =>
      import('./admin/admin.routes').then(m => m.ADMIN_ROUTES)
  },
  {
    path: 'electrician/login',
    loadComponent: () =>
      import('./electrician/login/electrician-login')
        .then(m => m.ElectricianLogin)
  },

  {
    path: 'electrician',
    canActivate:[ElectricianGuard],
    loadChildren: () =>
      import('./electrician/electrician.routes')
        .then(m => m.ELECTRICIAN_ROUTES)
  },
  {
    path: 'worker',
    loadChildren: () =>
      import('./worker/worker.routes')
        .then(m => m.WORKER_ROUTES)
  },
  // FALLBACK
  { path: '**', redirectTo: '' }
];
