import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AttendanceService {

  private baseUrl = environment.apiUrl + '/electrician/attendance';

  constructor(private http: HttpClient) { }

  markAttendance(data: any) {
    return this.http.post(`${this.baseUrl}/mark`, null, { params: data });
  }

  getByWorker(workerId: number) {
    return this.http.get<any[]>(`${this.baseUrl}/worker/${workerId}`);
  }

  getByWork(workId: number) {
    return this.http.get(`${this.baseUrl}/work/${workId}`);
  }
}
