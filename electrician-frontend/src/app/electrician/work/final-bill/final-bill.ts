import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FinalBillService } from '../../../core/services/final-bill.service';
import { OwnerPaymentService } from '../../../core/services/owner-payment.service';

@Component({
  selector: 'app-final-bill',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './final-bill.html',
  styleUrls: ['./final-bill.css']
})
export class FinalBill implements OnInit {

  @Input() workId!: number;

  summary: any = null;
  paymentsExist = false;

  amount!: number;
  date!: string;
  file!: File;
  remarks: string = '';

  constructor(
    private service: FinalBillService,
    private paymentService: OwnerPaymentService
  ) {}

  ngOnInit() {
    this.loadSummary();
    this.checkPayments();
  }
  
  onFileChange(event: any) {
  this.file = event.target.files[0];
}


  checkPayments() {
    this.paymentService.getByWork(this.workId)
      .subscribe((res: any[]) => {
        this.paymentsExist = res.length > 0;
      });
  }

  loadSummary() {
    this.service.getSummary(this.workId)
      .subscribe({
        next: (res) => this.summary = res,
        error: () => this.summary = null
      });
  }

  create() {
    const formData = new FormData();
    formData.append('workId', this.workId.toString());
    formData.append('amount', this.amount.toString());
    formData.append('date', this.date);
    formData.append('remarks', this.remarks);
    formData.append('billFile', this.file);


    this.service.createFinalBill(formData)
      .subscribe(() => {
        alert("Final Bill Created");
        this.loadSummary();
      });
  }

  download() {

  if (!this.summary?.billFileName) return;

  this.service.downloadBill(this.workId)
    .subscribe((blob: Blob) => {

      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = this.summary.billFileName;
      a.click();
      window.URL.revokeObjectURL(url);

    });
}


  getStatus(): string {

  if (!this.summary) return '';

  // Final bill not created but advance exists
  if (this.summary.finalBillAmount === 0 && this.summary.totalPaidByOwner > 0)
    return 'ADVANCE';

  if (this.summary.finalBillAmount === 0)
    return 'NO BILL';

  if (this.summary.remainingAmount === 0)
    return 'PAID';

  if (this.summary.totalPaidByOwner > 0)
    return 'PARTIAL';

  return 'PENDING';
}
}
