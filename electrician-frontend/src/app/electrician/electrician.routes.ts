import { Routes } from '@angular/router';
import { ElectricianLayout } from './layout/electrician-layout';

export const ELECTRICIAN_ROUTES: Routes = [
  {
    path: '',
    component: ElectricianLayout,
    children: [

      /* DASHBOARD */
      {
        path: 'dashboard',
        loadComponent: () =>
          import('./dashboard/electrician-dashboard')
            .then(m => m.ElectricianDashboard)
      },

      /* WORKERS */
      {
        path: 'workers',
        loadComponent: () =>
          import('./workers/worker-home/worker-home')
            .then(m => m.WorkerHome),
        children: [
          {
            path: '',
            redirectTo: 'list',
            pathMatch: 'full'
          },
          {
            path: 'list',
            loadComponent: () =>
              import('./workers/list-worker/list-worker')
                .then(m => m.ListWorker)
          },
          {
            path: 'add',
            loadComponent: () =>
              import('./workers/add-worker/add-worker')
                .then(m => m.AddWorker)
          },
          {
            path: 'edit/:id',
            loadComponent: () =>
              import('./workers/edit-worker/edit-worker')
                .then(m => m.EditWorker)
          }
        ]
      },

      /*  ATTENDANCE MODULE  */
      {
        path: 'attendance',
        loadChildren: () =>
          import('./attendance/attendance.routes')
            .then(m => m.ATTENDANCE_ROUTES)
      },
      {
        path: 'salary',
        loadComponent: () =>
          import('./salary/salary.component')
            .then(m => m.SalaryComponent)
      },
      {
        path: 'withdrawal/add',
        loadComponent: () =>
          import('./withdrawal/add-withdrawal/add-withdrawal')
            .then(m => m.AddWithdrawal)
      },
      {
        path: 'withdrawal/history',
        loadComponent: () =>
          import('./withdrawal/withdrawal-history/withdrawal-history')
            .then(m => m.WithdrawalHistory)
      },
      {
        path: 'work/add',
        loadComponent: () =>
          import('./work/add-work/add-work')
            .then(m => m.AddWork)
      },
      {
        path: 'work/list',
        loadComponent: () =>
          import('./work/list-work/list-work')
            .then(m => m.ListWork)
      },
      {
        path: 'work/:id',
        loadComponent: () =>
          import('./work/work-details/work-details')
            .then(m => m.WorkDetails)
      },


      /* DEFAULT */
      {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full'
      }
    ]
  }
];
