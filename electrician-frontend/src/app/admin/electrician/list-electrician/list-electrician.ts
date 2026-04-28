import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ElectricianService } from '../../../core/services/electrician.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'list-electrician',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './list-electrician.html',
  styleUrl: './list-electrician.css'
})
export class ListElectrician implements OnInit {

  electricians: any[] = [];

  constructor(
    private electricianService: ElectricianService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  cd=inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.loadElectricians();
  }

  loadElectricians(): void {
    this.electricianService.getAllElectricians()
      .subscribe(data => {
        this.electricians = data as any[];
        this.cd.detectChanges();
      });
  }

  subscribe(electricianId: number): void {
    // 🔥🔥 THIS IS THE FIX
    this.router.navigate(
      ['../subscribe-electrician', electricianId],
      { relativeTo: this.route }
    );
  }
}
  