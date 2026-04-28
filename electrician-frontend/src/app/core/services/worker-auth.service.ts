import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class WorkerAuthService {

  constructor(private router: Router) {}

  isLoggedIn(): boolean {
    return !!localStorage.getItem('workerToken');
  }

  logout() {
    localStorage.removeItem('workerToken');
    localStorage.removeItem('workerPhone');
    this.router.navigate(['/worker/login']);
  }
}