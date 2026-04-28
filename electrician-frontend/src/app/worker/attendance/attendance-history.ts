import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { WorkerAttendanceService } from './worker-attendance.service';

@Component({
  selector: 'app-worker-attendance-history',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './attendance-history.html',
  styleUrls: ['./attendance-history.css']
})
export class WorkerAttendanceHistory implements OnInit {

  worker: any;
  attendanceList: any[] = [];
  filteredList: any[] = [];

  selectedMonth: string = '';

  constructor(private attendanceService: WorkerAttendanceService,private cd: ChangeDetectorRef) {}

  ngOnInit(): void {

    const data = localStorage.getItem('worker');
    if (!data) return;

    this.worker = JSON.parse(data);

    // ✅ Auto current month (YYYY-MM)
    const today = new Date();
    this.selectedMonth =
      today.getFullYear() +
      '-' +
      String(today.getMonth() + 1).padStart(2, '0');

    this.attendanceService.getByWorker()
      .subscribe({
        next: (res: any[]) => {

          // ✅ Sort in ASCENDING order (oldest first)
          this.attendanceList = (res || []).sort((a, b) =>
            new Date(a.attendanceDate).getTime() -
            new Date(b.attendanceDate).getTime()
          );

          this.cd.detectChanges();
          this.filterByMonth();// apply auto month filter
        },
        error: () => {
          this.attendanceList = [];
          this.filteredList = [];
        }
      });
  }

  filterByMonth() {

    if (!this.selectedMonth) {
      this.filteredList = [...this.attendanceList];
      return;
    }

    this.filteredList = this.attendanceList.filter(a => {
      const date = new Date(a.attendanceDate);
      const month =
        date.getFullYear() +
        '-' +
        String(date.getMonth() + 1).padStart(2, '0');

      return month === this.selectedMonth;
    });
  }

  resetFilter() {
    this.selectedMonth = '';
    this.filteredList = [...this.attendanceList];
  }
}
