import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, Router, RouterModule } from '@angular/router';
import { ElectricianAuthService } from '../../core/services/electrician-auth.service';

@Component({
  selector: 'electrician-layout',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterModule],
  templateUrl: './electrician-layout.html',
  styleUrls: ['./electrician-layout.css']
})
export class ElectricianLayout implements OnInit {

  electrician: any = null;
  isSubscribed :boolean | null = null;

  constructor(
    private auth: ElectricianAuthService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {

    // If no token → redirect to login
    if (!this.auth.isLoggedIn()) {
      this.router.navigate(['/electrician/login']);
      return;
    }

    // Get profile from backend
    this.auth.getProfile().subscribe({
      next: (profile) => {
        this.electrician = profile;
        this.isSubscribed = profile.status === 'ACTIVE';
        this.cdr.detectChanges();
      },
      error: () => {
        this.auth.logout();
        this.router.navigate(['/electrician/login']);
      }
    });
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/']);
  }
}