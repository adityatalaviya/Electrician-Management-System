import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ElectricianService, Electrician } from '../../core/services/electrician.service';

@Component({
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home-dashboard.html',
  styleUrls: ['./home-dashboard.css']
})
export class HomeDashboard implements OnInit {

  totalElectricians = 0;
  activeElectricians = 0;

  threeMonthPlans = 0;
  sixMonthPlans = 0;
  twelveMonthPlans = 0;

  totalRevenue = 0;

  cd=inject(ChangeDetectorRef);

  constructor(private electricianService: ElectricianService) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData() {

    this.electricianService.getAllElectricians()
      .subscribe((list: Electrician[]) => {

        this.totalElectricians = list.length;

        this.activeElectricians =
          list.filter((e: Electrician) =>
            e.status === 'ACTIVE'
          ).length;

        this.threeMonthPlans =
          list.filter((e: Electrician) =>
            e.subscriptionPlan?.durationDays === 90
          ).length;

        this.sixMonthPlans =
          list.filter((e: Electrician) =>
            e.subscriptionPlan?.durationDays === 180
          ).length;

        this.twelveMonthPlans =
          list.filter((e: Electrician) =>
            e.subscriptionPlan?.durationDays === 365
          ).length;

        this.totalRevenue =
          list.reduce((sum: number, e: Electrician) =>
            sum + (e.subscriptionPlan?.price || 0),
          0);

          this.cd.detectChanges();

      });
  }
}