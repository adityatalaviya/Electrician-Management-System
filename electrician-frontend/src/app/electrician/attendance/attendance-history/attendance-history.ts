import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AttendanceService } from '../attendance.service';
import { WorkerService } from '../../../core/services/worker.service';
import { ElectricianAuthService } from '../../../core/services/electrician-auth.service';

@Component({
  selector: 'app-attendance-history',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './attendance-history.html',
  styleUrl: './attendance-history.css'
})
export class AttendanceHistoryComponent implements OnInit {

  electricianId!: number;

  workers: any[] = [];
  attendanceList: any[] = [];

  selectedWorkerId!: number;
  selectedMonth!: string; // yyyy-MM

  // calculated values
  totalDays = 0;
  presentDays = 0;
  absentDays = 0;

  constructor(
    private attendanceService: AttendanceService,
    private workerService: WorkerService,
    private authService: ElectricianAuthService
  ) {}

  ngOnInit(): void {
    const electrician = this.authService.getElectrician();
    this.electricianId = electrician.id;

    this.workerService.getWorkers(this.electricianId)
      .subscribe(res => this.workers = res);
  }

  loadAttendance() {

    if (!this.selectedWorkerId || !this.selectedMonth) {
      alert('Select worker and month');
      return;
    }

    this.attendanceService.getByWorker(this.selectedWorkerId)
      .subscribe(res => {
        this.filterByMonth(res as any[]);
        this.calculateSummary();
      });
  }

  filterByMonth(data: any[]) {
    this.attendanceList = data.filter(a =>
      a.attendanceDate.startsWith(this.selectedMonth)
    );
  }

  calculateSummary() {
    this.totalDays = 0;
    this.presentDays = 0;
    this.absentDays = 0;

    for (let a of this.attendanceList) {

      if (a.status === 'ABSENT') {
        this.absentDays += 1;
        continue;
      }

      if (a.session === 'FULL_DAY') {
        this.presentDays += 1;
      } else {
        this.presentDays += 0.5;
      }

      this.totalDays += 1;
    }
  }
}
