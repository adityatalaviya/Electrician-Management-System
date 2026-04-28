import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class WorkService {

  private baseUrl = environment.apiUrl + '/electrician/work';

  constructor(private http: HttpClient) {}

  addWork(electricianId: number, data: any) {
    return this.http.post(
      `${this.baseUrl}/add/${electricianId}`,
      data
    );
  }

  getWorks(electricianId: number) {
    return this.http.get<any[]>(
      `${this.baseUrl}/list/${electricianId}`
    );
  }

  completeWork(workId: number) {
    return this.http.put(
      `${this.baseUrl}/complete/${workId}`,
      {}
    );
  }
}
