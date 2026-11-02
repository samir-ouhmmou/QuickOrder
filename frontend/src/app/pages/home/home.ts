import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Hero } from './sections/hero/hero';
import { Chefs } from './sections/chefs/chefs';

interface Service {
  number: string;
  title: string;
  description: string;
}

interface GalleryImage {
  src: string;
  alt: string;
  title: string;
}

interface Testimonial {
  quote: string;
  occasion: string;
}

@Component({
  selector: 'app-home',
  imports: [Hero, Chefs, RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  readonly services: Service[] = [
    {
      number: '01',
      title: 'Weddings & celebrations',
      description:
        'A generous Moroccan table, thoughtfully prepared for the moments you want to remember.',
    },
    {
      number: '02',
      title: 'Private gatherings',
      description:
        'Bring family and friends together over traditional dishes made for sharing.',
    },
    {
      number: '03',
      title: 'Corporate events',
      description:
        'Give your guests a warm welcome with authentic flavours and attentive service.',
    },
  ];

  readonly gallery: GalleryImage[] = [
    { src: '/images/plats/tajine.png', alt: 'Moroccan tajine served at the table', title: 'Slow-cooked tajine' },
    { src: '/images/plats/pastilla.png', alt: 'Golden Moroccan pastilla', title: 'Golden pastilla' },
    { src: '/images/plats/conscous.png', alt: 'A traditional couscous dish', title: 'Friday couscous' },
    { src: '/images/plats/na3na3.png', alt: 'Fresh mint tea', title: 'Mint tea' },
  ];

  readonly testimonials: Testimonial[] = [
    {
      quote: 'Beautifully presented dishes and generous flavours made our family celebration feel truly special.',
      occasion: 'A family celebration',
    },
    {
      quote: 'The warm welcome and traditional menu brought everyone together around the table.',
      occasion: 'A private gathering',
    },
    {
      quote: 'A memorable way to share Moroccan cuisine with our guests.',
      occasion: 'A special event',
    },
  ];
}