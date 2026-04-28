import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class SalaryService {

  private baseUrl = environment.apiUrl + '/electrician/salary';

  constructor(private http: HttpClient) {}

  getSalaryByWorker(workerId: number) {
    return this.http.get<any>(`${this.baseUrl}/worker/${workerId}`);
  }
}
