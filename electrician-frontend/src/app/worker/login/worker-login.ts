import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-worker-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './worker-login.html',
  styleUrls: ['./worker-login.css']
})
export class WorkerLogin {

  phone = '';
  otp = '';

  step = 1;
  error = '';
  loading = false;

  constructor(
    private http: HttpClient,
    private router: Router,
    private cd: ChangeDetectorRef
  ) { }

  requestOtp() {

    this.error = '';

    if (!this.phone || this.phone.length !== 10) {
      this.error = 'Enter valid phone number';
      return;
    }

    this.loading = true;

    const params = new HttpParams().set('phone', this.phone);

    this.http.post(`${environment.apiUrl}/worker/request-otp`, null, { params, responseType: 'text' })
      .subscribe({
        next: () => {

          console.log("OTP request success");

          this.step = 2;   // ✅ SHOW OTP FIELD
          this.loading = false;

          this.cd.detectChanges(); // 🔥 force update
        },
        error: (err) => {

          this.loading = false;

          console.log("ERROR RESPONSE:", err);

          this.error = err?.error || 'Worker not registered';

          this.cd.detectChanges();
        }
      });
  }

  verifyOtp() {

  const params = new HttpParams()
    .set('phone', this.phone)
    .set('enteredOtp', this.otp);

  this.http.post(`${environment.apiUrl}/worker/verify-otp`, null,
    { params, responseType: 'text' })
    .subscribe({
      next: (token) => {

        localStorage.setItem('workerToken', token);

        // ✅ FETCH WORKER DETAILS
        this.http.get(`${environment.apiUrl}/worker/profile`)
          .subscribe((worker: any) => {

            localStorage.setItem('worker', JSON.stringify(worker));

            this.router.navigate(['/worker/dashboard']);
          });

      },
      error: () => {
        this.error = 'Invalid OTP';
      }
    });
}
}