import {
  AfterViewInit, Component, ElementRef, OnDestroy, PLATFORM_ID, ViewChild, inject, signal,
} from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { RouterLink } from '@angular/router';

interface Plat {
  name: string;
  description: string;
  price: number;
  image: string;
}

@Component({
  selector: 'app-welcome',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './welcome.html',
  styleUrl: './welcome.css',
})
export class Welcome implements AfterViewInit, OnDestroy {
  @ViewChild('catalogue') catalogue!: ElementRef<HTMLElement>;

  private platformId = inject(PLATFORM_ID);
  private observer?: IntersectionObserver;

  visible = signal(false);

  // Prix et descriptions indicatifs : à adapter
  plats: Plat[] = [
    {
      name: 'Pastilla aux fruits de mer',
      description: 'Fruits de mer, amandes, sucre glace.',
      price: 95,
      image: '/images/plats/pastilla.png',
    },
    {
      name: 'Couscous aux légumes',
      description: 'Couscous aux légumes du jour, servi avec une sauce piquante.',
      price: 80,
      image: '/images/plats/conscous.png',
    },
    {
      name: 'Pastilla au nougat',
      description: 'Feuilletée croustillante, crème légère et nougat, à déguster en dessert.',
      price: 110,
      image: '/images/plats/na3na3.png',
    },
    {
      name: 'Tajine barkouk',
      description: 'Tajine marocain aux pruneaux, amandes et miel.',
      price: 25,
      image: '/images/plats/tajine.png',
    },
  ];

  ngAfterViewInit(): void {
    // Côté serveur (SSR), il n'y a rien à observer
    if (!isPlatformBrowser(this.platformId)) return;

    // Navigateur sans IntersectionObserver : affichage direct
    if (typeof IntersectionObserver === 'undefined') {
      this.visible.set(true);
      return;
    }

    this.observer = new IntersectionObserver(
      ([entry]) => {
        if (entry.isIntersecting) {
          this.visible.set(true);
          this.observer?.disconnect();
        }
      },
      { threshold: 0.2 },
    );
    this.observer.observe(this.catalogue.nativeElement);
  }

  ngOnDestroy(): void {
    this.observer?.disconnect();
  }
}