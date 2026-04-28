import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterOutlet } from '@angular/router';
import { ElectricianAuthService } from '../../../core/services/electrician-auth.service';

@Component({
  selector: 'worker-home',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './worker-home.html'
})
export class WorkerHome {

  isSubscribed = false;

  constructor(
    private router: Router,
    private auth: ElectricianAuthService
  ) {
    this.isSubscribed = this.auth.isSubscribed();
  }

  goAdd() {
    this.router.navigate(['electrician/workers/add']);
  }

  goList() {
    this.router.navigate(['electrician/workers/list']);
  }
}
