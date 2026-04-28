import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WorkerWithdrawalService {

  private baseUrl = environment.apiUrl + '/worker/dashboard';

  constructor(private http: HttpClient) {}

  getByWorker() {
    return this.http.get<any[]>(`${this.baseUrl}/withdraw`);
  }
}