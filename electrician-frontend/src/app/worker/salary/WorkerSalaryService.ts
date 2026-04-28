import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WorkerSalaryService {

  private baseUrl = environment.apiUrl + '/worker/dashboard';

  constructor(private http: HttpClient) {}

  getSalaryByWorker() {
    return this.http.get(`${this.baseUrl}/salary`);
  }
}