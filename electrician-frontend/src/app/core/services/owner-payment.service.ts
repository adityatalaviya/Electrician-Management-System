import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OwnerPaymentService {

  private API = 'http://localhost:8080/electrician/owner-payment';

  constructor(private http: HttpClient) {}

  addPayment(data: any): Observable<any> {

    let params = new HttpParams()
      .set('workId', data.workId)
      .set('amount', data.amount)
      .set('date', data.date)
      .set('mode', data.mode)
      .set('remarks', data.remarks || '');

    return this.http.post(`${this.API}/add`, null, { params });
  }

  getByWork(workId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.API}/work/${workId}`);
  }
}
