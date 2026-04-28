import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { WorkerService } from '../../../core/services/worker.service';
import { WorkDetailsService } from '../../../core/services/work-details.service';
import { WithdrawalService } from '../withdrawal.service';
import { ElectricianAuthService } from '../../../core/services/electrician-auth.service';

@Component({
  selector: 'app-add-withdrawal',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-withdrawal.html',
  styleUrls: ['./add-withdrawal.css']
})
export class AddWithdrawal implements OnInit {

  electricianId!: number;

  workers: any[] = [];
  works: any[] = [];

  workerId!: number;
  workId!: number;
  amount!: number;
  remarks = '';
  date = new Date().toISOString().substring(0, 10);

  constructor(
    private workerService: WorkerService,
    private workService: WorkDetailsService,
    private withdrawalService: WithdrawalService,
    private authService: ElectricianAuthService,
    private cd:ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const electrician = this.authService.getElectrician();
    this.electricianId = electrician.id;

    this.workerService.getWorkers(this.electricianId)
      .subscribe(res => this.workers = res);

    this.workService.getWorksByElectrician(this.electricianId)
      .subscribe(res => {
        this.works = res.filter(w => w.status === 'ONGOING');
        this.cd.detectChanges();
      });
  }

  save() {
    if (!this.workerId || !this.workId || !this.amount) {
      alert('All fields are required');
      return;
    }

    this.withdrawalService.addWithdrawal({
      workerId: this.workerId,
      workId: this.workId,
      amount: this.amount,
      date: this.date,
      remarks: this.remarks
    }).subscribe(() => {
      alert('Withdrawal added successfully');
      this.cd.detectChanges();
      this.amount = 0;
      this.remarks = '';
    });
  }
}
