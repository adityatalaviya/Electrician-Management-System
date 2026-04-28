import { ChangeDetectorRef, Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { WorkerService } from '../../../core/services/worker.service';
import { ElectricianAuthService } from '../../../core/services/electrician-auth.service';

@Component({
  selector: 'add-worker',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-worker.html',
  styleUrls: ['./add-worker.css']
})
export class AddWorker {

  worker = {
    name: '',
    phone: '',
    dailyWage: 0
  };

  electrician: any;
  isSubscribed = false;
  error = '';

  constructor(
    private workerService: WorkerService,
    private auth: ElectricianAuthService,
    private router: Router,
    private cd:ChangeDetectorRef
  ) {
    this.electrician = this.auth.getElectrician();
    this.isSubscribed = this.auth.isSubscribed();
  }

  save() {
  if (!this.isSubscribed) {
    this.error = 'Please subscribe to unlock';
    return;
  }

  this.workerService
    .addWorker(this.worker, this.electrician.id)
    .subscribe({
      next: () => {
        this.cd.detectChanges(); // Ensure UI updates before navigation
        // 🔥 AUTO REDIRECT TO LIST
        this.router.navigate(['/electrician/workers/list']);
      },
      error: () => this.error = 'Failed to add worker'
    });
}

}
