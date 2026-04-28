import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { WorkerWithdrawalService } from './WorkerWithdrawalService';

@Component({
  selector: 'app-worker-withdrawal-history',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './withdrawal-history.html',
  styleUrls: ['./withdrawal-history.css']
})
export class WorkerWithdrawalHistory implements OnInit {

  worker: any = null;

  withdrawalList: any[] = [];
  filteredList: any[] = [];

  selectedMonth = '';

  totalWithdrawn = 0;

  loading = false;

  constructor(
    private workerWithdrawalService: WorkerWithdrawalService,
    private router: Router,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {

    const data = localStorage.getItem('worker');

    if (!data) {
      this.router.navigate(['/worker/login']);
      return;
    }

    this.worker = JSON.parse(data);

    // auto current month
    const today = new Date();
    this.selectedMonth =
      today.getFullYear() +
      '-' +
      String(today.getMonth() + 1).padStart(2, '0');

    this.loadWithdrawals();
  }

  loadWithdrawals(): void {

    this.loading = true;

    this.workerWithdrawalService
      .getByWorker()
      .subscribe({
        next: (res: any[]) => {

          // ascending order by date
          this.withdrawalList = res.sort((a, b) =>
            new Date(a.withdrawalDate).getTime() -
            new Date(b.withdrawalDate).getTime()
          );

          this.filterByMonth();
          this.loading = false;

          this.cd.detectChanges();
        },
        error: () => {
          this.loading = false;
          this.cd.detectChanges();
        }
      });
  }

  filterByMonth(): void {

    if (!this.selectedMonth) {
      this.filteredList = this.withdrawalList;
    } else {

      this.filteredList = this.withdrawalList.filter(w => {
        const date = new Date(w.withdrawalDate);
        const month =
          date.getFullYear() +
          '-' +
          String(date.getMonth() + 1).padStart(2, '0');

        return month === this.selectedMonth;
      });
    }

    this.calculateTotal();
  }

  calculateTotal(): void {
    this.totalWithdrawn = 0;

    this.filteredList.forEach(w => {
      this.totalWithdrawn += w.amount;
    });
  }

  resetFilter(): void {
    this.selectedMonth = '';
    this.filteredList = this.withdrawalList;
    this.calculateTotal();
  }
}
