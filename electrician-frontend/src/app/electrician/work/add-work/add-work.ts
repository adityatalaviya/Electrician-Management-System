import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { WorkService } from '../work.service';
import { ElectricianAuthService } from '../../../core/services/electrician-auth.service';

@Component({
  selector: 'app-add-work',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './add-work.html',
  styleUrls: ['./add-work.css']
})
export class AddWork implements OnInit {

  electricianId!: number;

  ownerName = '';
  location = '';
  category = '';

  saving = false;

  constructor(
    private workService: WorkService,
    private authService: ElectricianAuthService
  ) {}

  ngOnInit(): void {
    const electrician = this.authService.getElectrician();
    this.electricianId = electrician.id;
  }

  save() {
    if (!this.ownerName || !this.location || !this.category) {
      alert('All fields are required');
      return;
    }

    this.saving = true;

    this.workService.addWork(this.electricianId, {
      ownerName: this.ownerName,
      location: this.location,
      category: this.category
    }).subscribe(() => {
      alert('Work added successfully');

      this.ownerName = '';
      this.location = '';
      this.category = '';
      this.saving = false;
    });
  }
}
