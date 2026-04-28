import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class WithdrawalService {

  private baseUrl = environment.apiUrl + '/electrician/withdrawal';

  constructor(private http: HttpClient) {}

  addWithdrawal(data: any) {
    return this.http.post(`${this.baseUrl}/add`, null, {
      params: data
    });
  }

  getByWorker(workerId: number) {
    return this.http.get<any[]>(`${this.baseUrl}/worker/${workerId}`);
  }
}
