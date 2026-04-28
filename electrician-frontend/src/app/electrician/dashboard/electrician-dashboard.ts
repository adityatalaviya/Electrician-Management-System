import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ElectricianAuthService } from '../../core/services/electrician-auth.service';
import { WorkerService } from '../../core/services/worker.service';
import { WorkDetailsService } from '../../core/services/work-details.service';
import { WithdrawalService } from '../withdrawal/withdrawal.service';
import { SalaryService } from '../salary/salary.service';
import { FinalBillService } from '../../core/services/final-bill.service';

@Component({
  selector: 'electrician-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './electrician-dashboard.html',
  styleUrls: ['./electrician-dashboard.css']
})
export class ElectricianDashboard implements OnInit {

  electrician: any;
  isSubscribed: boolean | null = null;

  totalWorkers = 0;
  totalWorks = 0;
  totalSalary = 0;
  totalWithdrawals = 0;
  netPayable = 0;

  totalFinalBill = 0;
  totalOwnerPaid = 0;
  totalRemaining = 0;

  membershipEnd!: string | Date;
  loading = true;

  constructor(
    private auth: ElectricianAuthService,
    private workerService: WorkerService,
    private workService: WorkDetailsService,
    private withdrawalService: WithdrawalService,
    private salaryService: SalaryService,
    private finalBillService: FinalBillService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {

    this.loading = true;

    if (!this.auth.isLoggedIn()) {
      return;
    }

    this.auth.getProfile().subscribe({
      next: (profile) => {

        this.electrician = profile;
        this.membershipEnd = profile.membershipEnd;
        this.isSubscribed = profile.status === 'ACTIVE';

        if (this.isSubscribed) {
          this.loadDashboardData();
        } else {
          this.loading = false;
        }
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  loadDashboardData(): void {

    const electricianId = this.electrician.id;

    /* WORKERS */
    this.workerService.getWorkers(electricianId).subscribe(workers => {

      this.totalWorkers = workers.length;

      workers.forEach(worker => {

        this.salaryService.getSalaryByWorker(worker.id).subscribe(salary => {
          this.totalSalary += salary?.totalEarnedAmount || 0;
          this.updateNetPayable();
        });

        this.withdrawalService.getByWorker(worker.id).subscribe(list => {
          list.forEach(w => {
            this.totalWithdrawals += w.amount || 0;
          });
          this.updateNetPayable();
        });

      });

    });

    /* WORKS + FINAL BILL TOTALS */
    this.workService.getWorksByElectrician(electricianId).subscribe(works => {

      this.totalWorks = works.length;

      if (works.length === 0) {
        this.loading = false;
        this.cdr.detectChanges();
        return;
      }

      let completed = 0;

      works.forEach(work => {

        this.finalBillService.getSummary(work.id).subscribe(summary => {

          this.totalFinalBill += summary?.finalBillAmount || 0;
          this.totalOwnerPaid += summary?.totalPaidByOwner || 0;
          this.totalRemaining += summary?.remainingAmount || 0;

          completed++;

          if (completed === works.length) {
            this.loading = false;
            this.cdr.detectChanges();
          }

        }, () => {

          completed++;

          if (completed === works.length) {
            this.loading = false;
            this.cdr.detectChanges();
          }

        });

      });

    });
  }

  updateNetPayable(): void {
    this.netPayable = this.totalSalary - this.totalWithdrawals;
  }
}
