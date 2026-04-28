import { Routes } from '@angular/router';

export const ATTENDANCE_ROUTES: Routes = [

  {
    path: 'mark',
    loadComponent: () =>
      import('./mark-attendance/mark-attendance')
        .then(m => m.MarkAttendanceComponent)
  },

  {
    path: 'history',
    loadComponent: () =>
      import('./attendance-history/attendance-history')
        .then(m => m.AttendanceHistoryComponent)
  },

  {
    path: '',
    redirectTo: 'mark',
    pathMatch: 'full'
  }
];
