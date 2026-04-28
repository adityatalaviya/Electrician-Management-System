import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Observable } from 'rxjs';

import { SubscriptionPlanService } from '../../../core/services/subscription-plan.service';
import { ElectricianService } from '../../../core/services/electrician.service';
import { SubscriptionPlan } from '../../../core/models/subscription-plan.model';

@Component({
  selector: 'app-subscribe-electrician',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './subscribe-electrician.html',
  styleUrl: './subscribe-electrician.css'
})
export class SubscribeElectrician implements OnInit {

  electricianId!: number;
  plans$!: Observable<SubscriptionPlan[]>;

  constructor(
    private route: ActivatedRoute,
    private planService: SubscriptionPlanService,
    private electricianService: ElectricianService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.electricianId = Number(this.route.snapshot.paramMap.get('id'));
    this.plans$ = this.planService.getAllPlans();
  }

  subscribe(planId: number): void {
    this.electricianService.subscribeElectrician(this.electricianId, planId)
      .subscribe({
        next: () => {
          alert('Subscription successful!');
          this.router.navigate(['/admin/electricians']);
        },
        error: () => {
          alert('Subscription failed');
        }
      });
  }
}
