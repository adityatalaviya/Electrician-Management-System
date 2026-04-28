import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { AttendanceService } from '../attendance.service';
import { WorkerService } from '../../../core/services/worker.service';
import { WorkDetailsService } from '../../../core/services/work-details.service';
import { ElectricianAuthService } from '../../../core/services/electrician-auth.service';

@Component({
  selector: 'app-mark-attendance',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './mark-attendance.html',
  styleUrls: ['./mark-attendance.css']
})
export class MarkAttendanceComponent implements OnInit {

  date = new Date().toISOString().substring(0, 10);

  electricianId!: number;

  workers: any[] = [];
  works: any[] = [];

  constructor(
    private attendanceService: AttendanceService,
    private workerService: WorkerService,
    private workService: WorkDetailsService,
    private authService: ElectricianAuthService,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const electrician = this.authService.getElectrician();

    if (!electrician) {
      alert('Electrician not logged in');
      return;
    }

    this.electricianId = electrician.id;

    this.loadWorks();
    this.loadWorkers();
  }

  loadWorkers(): void {
    this.workerService.getWorkers(this.electricianId)
      .subscribe((res: any[]) => {
        this.workers = res.map(w => ({
          ...w,
          status: 'PRESENT',
          session: 'FULL_DAY',
          workId: null   
        }));
        this.cd.detectChanges();
      });
  }

  loadWorks(): void {
    this.workService.getWorksByElectrician(this.electricianId)
      .subscribe((res: any[]) => {
        this.works = res.filter(w => w.status === "ONGOING");
        this.cd.detectChanges();
      });
  }

  submit(): void {

    for (let w of this.workers) {

    //present → work site needed
      if (!w.workId) {
        alert(`Please select work site for ${w.name}`);
        return;
      }

      this.attendanceService.markAttendance({
        workerId: w.id,
        workId: w.workId,
        date: this.date,
        status: w.status,
        session: w.session
      }).subscribe();
    }

    alert('Attendance marked successfully');
    this.cd.detectChanges();
  }
}
