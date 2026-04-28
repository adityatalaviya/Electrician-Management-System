import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'admin-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
  
})
export class AdminLogin {

  email: string = '';
  password: string = '';
  error: string = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  login() {
  this.error = '';

  this.authService.login({
    email: this.email,
    password: this.password
  }).subscribe({
    next: (res: any) => {

      // 🔥 Save JWT
      this.authService.setToken(res.token);

      // Go to admin dashboard
      this.router.navigate(['/admin']);

    },
    error: () => {
      this.error = 'Invalid email or password';
    }
  });
}


}
