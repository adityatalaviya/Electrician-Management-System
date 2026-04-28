import { Routes } from '@angular/router';
import { workerAuthGuard } from '../core/guards/worker-auth.guard';

export const WORKER_ROUTES: Routes = [

  {
    path: 'login',
    loadComponent: () =>
      import('./login/worker-login')
        .then(m => m.WorkerLogin)
  },

  {
    path: 'dashboard',
    canActivate: [workerAuthGuard],
    loadComponent: () =>
      import('./dashboard/worker-dashboard')
        .then(m => m.WorkerDashboard)
  },

  {
    path: 'attendance',
    canActivate: [workerAuthGuard],
    loadComponent: () =>
      import('./attendance/attendance-history')
        .then(m => m.WorkerAttendanceHistory)
  },

  {
    path: 'salary',
    canActivate: [workerAuthGuard],
    loadComponent: () =>
      import('./salary/worker-salary')
        .then(m => m.WorkerSalary)
  },

  {
    path: 'withdrawals',
    canActivate: [workerAuthGuard],
    loadComponent: () =>
      import('./withdrawal/withdrawal-history')
        .then(m => m.WorkerWithdrawalHistory)
  },

  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  }
];