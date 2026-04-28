import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

import { ElectricianAuthService } from '../../core/services/electrician-auth.service';

@Component({
  selector: 'electrician-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './electrician-login.html',
  styleUrls: ['./electrician-login.css']
})
export class ElectricianLogin {

  phone = '';
  otp = '';

  otpSent = false;
  loading = false;
  error = '';

  constructor(
    private auth: ElectricianAuthService,
    private router: Router,
    private cd: ChangeDetectorRef
  ) {}

  requestOtp() {

    if (!this.phone) {
      this.error = "Enter phone number";
      return;
    }

    this.loading = true;
    this.error = '';

    this.auth.requestOtp(this.phone).subscribe({

      next: () => {

        this.loading = false;
        this.otpSent = true;

        this.cd.detectChanges(); // ⭐ FORCE UI UPDATE

      },

      error: () => {

        this.loading = false;
        this.error = "OTP send failed";

        this.cd.detectChanges(); // ⭐ UPDATE UI

      }

    });

  }

  verifyOtp() {

    if (!this.otp) {
      this.error = "Enter OTP";
      return;
    }

    this.loading = true;

    this.auth.verifyOtp(this.phone, this.otp).subscribe({

      next: (token: string) => {

        this.auth.setToken(token);
        this.cd.detectChanges(); // ⭐ UPDATE UI
        
        this.router.navigate(['/electrician/dashboard']);
        this.cd.detectChanges(); // ⭐ UPDATE UI  

      },

      error: () => {

        this.loading = false;
        this.error = "Invalid OTP";

        this.cd.detectChanges(); // ⭐ UPDATE UI

      }

    });

  }

}