import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { QuotationService } from '../../../core/services/quotation.service';


@Component({
  selector: 'app-quotation',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './quotation.html',
  styleUrls: ['./quotation.css']
})
export class Quotation implements OnInit {

  @Input() workId!: number;

  quotations: any[] = [];
  file!: File;
  remarks: string = '';

  constructor(private service: QuotationService) {}

  ngOnInit() {
    this.loadQuotations();
  }

  loadQuotations() {
    this.service.getByWork(this.workId)
      .subscribe(res => this.quotations = res as any[]);
  }

  onFileChange(event: any) {
    this.file = event.target.files[0];
  }

  upload() {
    if (!this.file) return alert("Select file first");

    const formData = new FormData();
    formData.append('file', this.file);
    formData.append('remarks', this.remarks);

    this.service.uploadQuotation(this.workId, formData)
      .subscribe(() => {
        this.loadQuotations();
        this.remarks = '';
        alert("Quotation Uploaded");
      });
  }

  download(id: number, fileName: string) {
  this.service.downloadQuotation(id)
    .subscribe((blob: Blob) => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = fileName;
      a.click();
      window.URL.revokeObjectURL(url);
    });
}


}
