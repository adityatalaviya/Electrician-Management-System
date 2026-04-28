import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class WorkDetailsService {

  private baseUrl = environment.apiUrl + '/electrician/work';

  constructor(private http: HttpClient) {}

  getWorksByElectrician(electricianId: number) {
  return this.http.get<any[]>(`${this.baseUrl}/list/${electricianId}`);
}

}
