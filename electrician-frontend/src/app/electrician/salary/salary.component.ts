import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { SalaryService } from './salary.service';
import { WorkerService } from '../../core/services/worker.service';
import { ElectricianAuthService } from '../../core/services/electrician-auth.service';

@Component({
  selector: 'app-salary',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './salary.component.html',
  styleUrls: ['./salary.component.css']
})
export class SalaryComponent implements OnInit {

  electricianId!: number;

  workers: any[] = [];
  selectedWorkerId!: number;

  salary: any = null;
  //loading = false;

  constructor(
    private salaryService: SalaryService,
    private workerService: WorkerService,
    private authService: ElectricianAuthService,private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const electrician = this.authService.getElectrician();
    if (!electrician) return;

    this.electricianId = electrician.id;

    this.workerService.getWorkers(this.electricianId)
      .subscribe(res => this.workers = res);
  }

  viewSalary() {
    if (!this.selectedWorkerId) {
      alert('Please select a worker');
      return;
    }

    //this.loading = true;
    this.salaryService.getSalaryByWorker(this.selectedWorkerId)
      .subscribe(res => {
          this.salary = res;
          this.cd.detectChanges();
      //  this.loading = false;
      });
  }
}
