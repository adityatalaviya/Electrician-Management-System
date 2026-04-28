import { Routes } from '@angular/router';
import { AdminDashboard } from './dashboard/admin-dashboard/admin-dashboard';

export const ADMIN_ROUTES: Routes = [
  {
    path: '',
    component: AdminDashboard,
    children: [

      // 🔥 Default Dashboard
      {
        path: '',
        loadComponent: () =>
          import('./home/home-dashboard')
            .then(m => m.HomeDashboard)
      },

      {
        path: 'electricians',
        loadComponent: () =>
          import('./electrician/list-electrician/list-electrician')
            .then(m => m.ListElectrician)
      },

      {
        path: 'add-electrician',
        loadComponent: () =>
          import('./electrician/add-electrician/add-electrician')
            .then(m => m.AddElectrician)
      },

      {
        path: 'subscribe-electrician/:id',
        loadComponent: () =>
          import('./electrician/subscribe-electrician/subscribe-electrician')
            .then(m => m.SubscribeElectrician)
      }

    ]
  }
];