import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';

export interface SubscriptionPlan {
  id: number;
  planName: string;
  durationDays: number;
  price: number;
}

export interface Electrician {
  id: number;
  name: string;
  phone: string;
  status: string;
  membershipEnd: string;
  subscriptionPlan?: SubscriptionPlan;
}

@Injectable({ providedIn: 'root' })
export class ElectricianService {

  private baseUrl = environment.apiUrl + '/admin/electrician';

  constructor(private http: HttpClient) {}

  getAllElectricians(): Observable<Electrician[]> {
    return this.http.get<Electrician[]>(`${this.baseUrl}/all`);
  }

  addElectrician(data: any) {
    return this.http.post(`${this.baseUrl}/add`, data);
  }

  subscribeElectrician(electricianId: number, planId: number) {
    return this.http.post(
      `${this.baseUrl}/subscribe/${electricianId}/${planId}`,
      {}
    );
  }
}