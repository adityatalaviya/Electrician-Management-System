import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { WithdrawalService } from '../withdrawal.service';
import { WorkerService } from '../../../core/services/worker.service';
import { ElectricianAuthService } from '../../../core/services/electrician-auth.service';

@Component({
  selector: 'app-withdrawal-history',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './withdrawal-history.html',
  styleUrls: ['./withdrawal-history.css']
})
export class WithdrawalHistory implements OnInit {

  electricianId!: number;
  workers: any[] = [];
  selectedWorkerId!: number;

  withdrawals: any[] = [];

  constructor(
    private withdrawalService: WithdrawalService,
    private workerService: WorkerService,
    private authService: ElectricianAuthService
  ) {}

  ngOnInit(): void {
    const electrician = this.authService.getElectrician();
    this.electricianId = electrician.id;

    this.workerService.getWorkers(this.electricianId)
      .subscribe(res => this.workers = res);
  }

  load() {
    if (!this.selectedWorkerId) {
      alert('Select worker');
      return;
    }

    this.withdrawalService.getByWorker(this.selectedWorkerId)
      .subscribe(res => this.withdrawals = res);
  }
}
