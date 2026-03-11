import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: false,
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  constructor(private router: Router) { }

  navigateToLogin() {
    // Navigate to login page
    this.router.navigate(['/login']);
  }

  getDetails() {
    // Navigate to details page for now
    this.router.navigate(['/details']);
  }
}
