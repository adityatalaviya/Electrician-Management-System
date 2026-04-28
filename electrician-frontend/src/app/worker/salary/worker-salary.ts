import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { WorkerSalaryService } from './WorkerSalaryService';

@Component({
  selector: 'app-worker-salary',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './worker-salary.html',
  styleUrls: ['./worker-salary.css']
})
export class WorkerSalary implements OnInit {

  worker: any = null;
  salary: any = null;
  loading = false;

  constructor(
    private workerSalaryService: WorkerSalaryService,
    private router: Router,
    private cd: ChangeDetectorRef   // 👈 IMPORTANT
  ) {}

  ngOnInit(): void {

    const data = localStorage.getItem('worker');

    if (!data) {
      this.router.navigate(['/worker/login']);
      return;
    }

    this.worker = JSON.parse(data);

    this.loadSalary();
  }

  loadSalary(): void {

    this.loading = true;

    this.workerSalaryService.getSalaryByWorker()
      .subscribe({
        next: (res: any) => {
          console.log('Salary Data:', res);

          this.salary = res;
          this.loading = false;

          this.cd.detectChanges();   // 👈 FORCE UI UPDATE
        },
        error: (err) => {
          console.error(err);
          this.loading = false;
          this.cd.detectChanges();
        }
      });
  }
}
