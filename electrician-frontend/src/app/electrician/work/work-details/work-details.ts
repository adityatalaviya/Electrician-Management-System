import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Quotation } from '../quotation/quotation';
import { FinalBill } from '../final-bill/final-bill';
import { OwnerPayment } from '../owner-payment/owner-payment';

@Component({
  selector: 'app-work-details',
  standalone: true,
  imports: [CommonModule, Quotation, FinalBill, OwnerPayment],
  templateUrl: './work-details.html',
  styleUrls: ['./work-details.css']
})
export class WorkDetails implements OnInit {

  workId!: number;
  activeTab: string = 'quotation';

  constructor(private route: ActivatedRoute) {}

  ngOnInit() {
    this.workId = Number(this.route.snapshot.paramMap.get('id'));
  }

  setTab(tab: string) {
    this.activeTab = tab;
  }
}
