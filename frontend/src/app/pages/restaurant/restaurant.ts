import { Component, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-restaurant',
  imports: [FormsModule, RouterLink],
  templateUrl: './restaurant.html',
  styleUrl: './restaurant.css',
})
export class Restaurant {
  readonly reservationPrepared = signal(false);

  submitReservation(): void {
    this.reservationPrepared.set(true);
  }
}
