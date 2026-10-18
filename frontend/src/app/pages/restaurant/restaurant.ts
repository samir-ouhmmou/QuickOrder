import { Component, computed, signal } from '@angular/core';
import { OSTERIA } from './restaurant.data';

@Component({
  selector: 'app-restaurant',
  templateUrl: './restaurant.html',
  styleUrl: './restaurant.css',
})
export class Restaurant {
  protected readonly r = OSTERIA;

  protected readonly activeCategory = signal(this.r.menu[0].id);
  protected readonly activeItems = computed(
    () => this.r.menu.find((c) => c.id === this.activeCategory())?.items ?? [],
  );

  protected readonly today = new Date().getDay();
  protected readonly ratingLabel = this.r.rating.toFixed(1).replace('.', ',');

  protected selectCategory(id: string): void {
    this.activeCategory.set(id);
  }

  protected formatPrice(amount: number): string {
    const value = Number.isInteger(amount) ? amount.toString() : amount.toFixed(2).replace('.', ',');
    return `${value} €`;
  }

  protected scrollTo(event: Event, id: string): void {
    event.preventDefault();
    document.getElementById(id)?.scrollIntoView({ behavior: 'smooth', block: 'start' });
  }
}