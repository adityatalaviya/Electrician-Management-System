import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class QuotationService {

  private API = 'http://localhost:8080/electrician/quotation';

  constructor(private http: HttpClient) {}

  uploadQuotation(workId: number, formData: FormData): Observable<any> {
    return this.http.post(`${this.API}/upload/${workId}`, formData);
  }

  getByWork(workId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.API}/work/${workId}`);
  }

  downloadQuotation(id: number) {
  return this.http.get(
    `http://localhost:8080/electrician/quotation/download/${id}`,
    { responseType: 'blob' }
  );
}


}
