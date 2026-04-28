import { ChangeDetectorRef, Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ElectricianService } from '../../../core/services/electrician.service';
import { Router } from '@angular/router';

@Component({
  selector: 'add-electrician',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './add-electrician.html',
  styleUrl: './add-electrician.css'
})
export class AddElectrician {

  electrician = {
    name: '',
    phone: ''
  };

  constructor(private electricianService: ElectricianService,
    private cd: ChangeDetectorRef,
    private router: Router
  ) { }

  submit(form: any) {

    if (form.invalid) {
      alert('Please fill all required fields correctly');
      return;
    }
    this.electricianService.addElectrician(this.electrician).subscribe({
      next: () => {
        alert('Electrician added');
        this.cd.detectChanges();
        form.resetForm();
        this.router.navigate(['/admin/electricians']);
        //this.electrician = { name: '', phone: '' };
      },
      error: () => {
        alert('Error adding electrician');
      }
    });
  }
}
