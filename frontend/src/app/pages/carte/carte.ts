import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

interface Category {
  title: string;
  count: number;
  link: string;
  image: string;
}

@Component({
  selector: 'app-carte',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './carte.html',
  styleUrl: './carte.css',
})
export class Carte {
  categories: Category[] = [
    { title: 'Entrées & Salades',     count: 4, link: '/carte/entrees',   image: 'images/entrees.jpg' },
    { title: 'Tajines',               count: 4, link: '/carte/tajines',   image: 'images/tajines.jpg' },
    { title: 'Couscous',              count: 4, link: '/carte/couscous',  image: 'images/couscous.jpg' },
    { title: 'Pastillas & Grillades', count: 4, link: '/carte/pastillas', image: 'images/pastillas.jpg' },
    { title: 'Desserts',              count: 4, link: '/carte/desserts',  image: 'images/desserts.jpg' },
    { title: 'Boissons',              count: 4, link: '/carte/boissons',  image: 'images/boissons.jpg' },
  ];
}