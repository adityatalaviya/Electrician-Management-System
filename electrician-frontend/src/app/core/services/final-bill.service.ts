import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FinalBillService {

  private API = 'http://localhost:8080/electrician/final-bill';

  constructor(private http: HttpClient) { }

  createFinalBill(formData: FormData): Observable<any> {
    return this.http.post(`${this.API}/create`, formData);
  }

  downloadBill(workId: number) {
  return this.http.get(
    `${this.API}/download/${workId}`,
    { responseType: 'blob' }
  );
}



  getSummary(workId: number): Observable<any> {
    return this.http.get(`${this.API}/summary/${workId}`);
  }
}
