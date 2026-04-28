import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private baseUrl = environment.apiUrl + '/admin';

  constructor(private http: HttpClient) {}

  // 🔹 Login API
  login(data: any) {
    return this.http.post<any>(`${this.baseUrl}/login`, data);
  }

  // 🔹 Save JWT Token
  setToken(token: string) {
    localStorage.setItem('token', token);
  }

  // 🔹 Get JWT Token
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  // 🔹 Check Login
  isAdminLoggedIn(): boolean {
    return !!this.getToken();
  }

  // 🔹 Logout
  logout() {
    localStorage.removeItem('token');
  }
}