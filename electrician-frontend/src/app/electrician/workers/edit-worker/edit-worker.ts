import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule, NgIf } from '@angular/common';
import { FormsModule, NgModel } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { WorkerService } from '../../../core/services/worker.service';

@Component({
  selector: 'edit-worker',
  standalone: true,
  imports: [CommonModule, FormsModule ],
  templateUrl: './edit-worker.html'
})
export class EditWorker implements OnInit {

  workerId!: number;
  //worker:any=null;
  worker = {
    name: '',
    phone: '',
    dailyWage: 0
  };

  loading = true;
  error = '';

  constructor(
    private route: ActivatedRoute,
    private workerService: WorkerService,
    private router: Router,
    private cd: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.workerId = Number(this.route.snapshot.paramMap.get('id'));

    this.workerService.getWorkerById(this.workerId).subscribe({
      next: (res: any) => {
        console.log("Worker:res", res);

      //   this.worker = {
      //   name: res.name,
      //   phone: res.phone,
      //   dailyWage: res.dailyWage
      // };
         this.worker = res;
  
        this.loading = false;
      },
      error: () => {
        this.error = 'Worker not found';
        this.loading = false;
      }
    });
  }

  update() {
    this.workerService.updateWorker(this.workerId, this.worker).subscribe({
      next: () => {
        alert('Worker updated successfully');
        this.cd.detectChanges(); // Ensure UI updates before navigation
        this.router.navigate(['/electrician/workers/list']);
      },
      error: () => {
        this.error = 'Update failed';
      }
    });
  }
}
