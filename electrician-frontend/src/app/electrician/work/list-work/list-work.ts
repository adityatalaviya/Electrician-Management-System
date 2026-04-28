import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

import { WorkService } from '../work.service';
import { ElectricianAuthService } from '../../../core/services/electrician-auth.service';

@Component({
  selector: 'app-list-work',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './list-work.html',
  styleUrls: ['./list-work.css']
})
export class ListWork implements OnInit {

  electricianId!: number;
  works: any[] = [];

  constructor(
    private workService: WorkService,
    private authService: ElectricianAuthService,
    private router: Router,
    private cd:ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    const electrician = this.authService.getElectrician();
    this.electricianId = electrician.id;
    this.loadWorks();
  }

  loadWorks() {
    this.workService.getWorks(this.electricianId)
      .subscribe(res => this.works = res);
      this.cd.detectChanges();
  }

  markCompleted(workId: number) {
    if (!confirm('Mark this work as completed?')) return;

    this.workService.completeWork(workId)
      .subscribe(() => this.loadWorks());
  }

  openWork(workId: number) {
    this.router.navigate(['/electrician/work', workId]);
  }
}
