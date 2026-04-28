import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { WorkerService } from '../../../core/services/worker.service';
import { ElectricianAuthService } from '../../../core/services/electrician-auth.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'list-worker',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './list-worker.html',
  styleUrl: './list-worker.css'
})
export class ListWorker implements OnInit {

  workers: any[] = [];
  electrician: any;
  isSubscribed = false;

  constructor(
    private workerService: WorkerService,
    private auth: ElectricianAuthService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.electrician = this.auth.getElectrician();
    this.isSubscribed = this.auth.isSubscribed();

    if (!this.electrician?.id) {
      this.router.navigate(['/electrician/login']);
      return;
    }

    
    this.workerService.getWorkers(this.electrician.id)
      .subscribe((res:any) => {
        console.log('WORKERS FROM API:', res);
        this.workers = res as any[];
      });
  }

  goAdd() {
    this.router.navigate(['/electrician/workers/add']);
  }

  trackById(index: number, worker: any) {
    return worker.id;
  }

  edit(worker: any) {
  this.router.navigate(['../edit', worker.id], {
    relativeTo: this.route
  });
}



  remove(id: number) {
  if (!confirm('Delete this worker?')) return;

  this.workerService.deleteWorker(id).subscribe(() => {
    this.workers = this.workers.filter(w => w.id !== id);
  });
}

}
