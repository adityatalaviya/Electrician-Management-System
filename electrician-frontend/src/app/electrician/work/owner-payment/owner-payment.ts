import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { OwnerPaymentService } from '../../../core/services/owner-payment.service';
import { FinalBillService } from '../../../core/services/final-bill.service';

@Component({
  selector: 'app-owner-payment',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './owner-payment.html',
  styleUrls: ['./owner-payment.css']
})
export class OwnerPayment implements OnInit {

  @Input() workId!: number;

  payments: any[] = [];
  summary: any;

  amount!: number;
  date!: string;
  mode: string = 'CASH';
  remarks: string = '';

  constructor(
    private service: OwnerPaymentService,
    private billService: FinalBillService
  ) { }

  ngOnInit() {
    this.loadPayments();
    this.loadSummary();
  }

  loadPayments() {
    this.service.getByWork(this.workId)
      .subscribe((res: any[]) => this.payments = res);
  }

  loadSummary() {
    this.billService.getSummary(this.workId)
      .subscribe((res: any) => this.summary = res);
  }

  add() {

    // Prevent overpayment only when final bill exists
    if (this.summary && this.summary.finalBillAmount > 0) {

      if (this.amount > this.summary.remainingAmount) {
        alert("❌ Overpayment not allowed!");
        return;
      }

    }

    this.service.addPayment({
      workId: this.workId,
      amount: this.amount,
      date: this.date,
      mode: this.mode,
      remarks: this.remarks
    }).subscribe(() => {
      alert("✅ Payment Added");
      this.amount = 0;
      this.remarks = '';
      this.loadPayments();
      this.loadSummary(); // 🔥 AUTO REFRESH BILL
    });
  }
}
