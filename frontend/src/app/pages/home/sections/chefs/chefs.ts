import { Component } from '@angular/core';

interface Chef {
  name: string;
  role: string;
  bio: string;
  photo: string;
}

@Component({
  selector: 'app-chefs',
  templateUrl: './chefs.html',
  styleUrl: './chefs.css',
})
export class Chefs {
  chefs: Chef[] = [
    {
      name: 'Chef Taoufik El mansouri',
      role: 'Executive Chef',
      bio: 'With over fifteen years of experience in traditional Moroccan kitchens, Youssef masters slow-cooked tajines and festive banquets. Every dish he creates carries the flavors of the recipes passed down through his family.',
      photo: '/images/samirxtaoufik.jpg',
    },
    {
      name: 'Chef Athmane Mezine',
      role: 'Pastry & Catering Chef',
      bio: 'Salma brings elegance to every event, from refined pastillas to delicate Moroccan pastries. Her passion is to blend authentic ingredients with a modern presentation for unforgettable celebrations.',
      photo: '/images/samirxtaoufik.jpg',
    },
  ];
}