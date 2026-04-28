import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { ElectricianAuthService } from '../services/electrician-auth.service';

@Injectable({ providedIn: 'root' })
export class ElectricianGuard implements CanActivate {

  constructor(
    private auth: ElectricianAuthService,
    private router: Router
  ) {}

  canActivate(): boolean {

    if (this.auth.isLoggedIn()) {
      return true;
    }

    this.router.navigate(['/electrician/login']);
    return false;
  }
}