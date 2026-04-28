import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ElectricianAuthService {

  private baseUrl = environment.apiUrl + '/electrician';

  constructor(private http: HttpClient) {}

  // ================= OTP =================

  requestOtp(phone: string) {
    return this.http.post(
      `${this.baseUrl}/request-otp?phone=${phone}`,
      {},
      { responseType: 'text' }
    );
  }

  verifyOtp(phone: string, otp: string) {
    return this.http.post(
      `${this.baseUrl}/verify-otp?phone=${phone}&otp=${otp}`,
      {},
      { responseType: 'text' }
    );
  }

  // ================= TOKEN =================

  setToken(token: string) {
    localStorage.setItem('electricianToken', token);
  }

  getToken(): string | null {
    return localStorage.getItem('electricianToken');
  }

  logout() {
    localStorage.removeItem('electricianToken');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  // ================= JWT DECODE =================

  private decodeToken() {

    const token = this.getToken();

    if (!token) return null;

    const payload = JSON.parse(atob(token.split('.')[1]));

    return payload;
  }

  // ⭐ THIS FIXES MANY ERRORS
  getElectrician() {

    const payload = this.decodeToken();

    if (!payload)
      throw new Error("Electrician not logged in");

    return {
      id: payload.id,
      phone: payload.sub,
      role: payload.role
    };

  }

  // ⭐ temporary subscription check
  isSubscribed(): boolean {
    return true;
  }

  // ================= PROFILE =================

  getProfile() {
    return this.http.get<any>(`${this.baseUrl}/profile`);
  }

}