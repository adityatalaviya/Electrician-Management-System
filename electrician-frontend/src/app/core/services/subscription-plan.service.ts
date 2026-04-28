import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { SubscriptionPlan } from '../models/subscription-plan.model';

@Injectable({ providedIn: 'root' })
export class SubscriptionPlanService {

  private baseUrl = environment.apiUrl + '/admin/plan';

  constructor(private http: HttpClient) {}

  getAllPlans() {
    return this.http.get<SubscriptionPlan[]>(`${this.baseUrl}/all`);
  }
}
