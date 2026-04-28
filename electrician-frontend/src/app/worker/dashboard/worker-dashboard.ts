import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { WorkerAuthService } from '../../core/services/worker-auth.service';

@Component({
  selector: 'app-worker-dashboard',
  standalone: true,
  imports: [CommonModule,RouterModule],
  templateUrl: './worker-dashboard.html',
  styleUrls: ['./worker-dashboard.css']
})
export class WorkerDashboard implements OnInit {

  worker: any;

  constructor(private auth: WorkerAuthService) {}

  ngOnInit(): void {

  const data = localStorage.getItem('worker');

  if (!data) {
    this.auth.logout();
    return;
  }

  this.worker = JSON.parse(data);
}

  logout() {
    this.auth.logout();
  }


}
