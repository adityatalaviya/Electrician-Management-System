import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class WorkerService {

  private baseUrl = environment.apiUrl + '/electrician/worker';

  constructor(private http: HttpClient) {}

  // ADD
  addWorker(worker: any, electricianId: number) {
    return this.http.post(
      `${this.baseUrl}/add/${electricianId}`,
      worker
    );
  }

  // LIST
  getWorkers(electricianId: number) {
    return this.http.get<any[]>(
      `${this.baseUrl}/list/${electricianId}`
    );
  }

  // ✅ GET SINGLE WORKER (🔥 REQUIRED FOR EDIT)
  getWorkerById(workerId: number) {
    return this.http.get<any>(
      `${this.baseUrl}/${workerId}`
    );
  }

  // UPDATE
  updateWorker(workerId: number, worker: any) {
    return this.http.put(
      `${this.baseUrl}/update/${workerId}`,
      worker
    );
  }

  // DELETE
  deleteWorker(workerId: number) {
    return this.http.delete(
      `${this.baseUrl}/delete/${workerId}`
    );
  }
}
