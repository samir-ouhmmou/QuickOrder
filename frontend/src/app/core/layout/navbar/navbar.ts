import { Component, HostListener, signal } from '@angular/core';
import { RouterLink } from '@angular/router';

interface NavLink {
  label: string;
  path: string;
  fragment?: string;
}

@Component({
  selector: 'app-navbar',
  imports: [RouterLink],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  scrolled = signal(false);
  menuOpen = signal(false);

  links: NavLink[] = [
    { label: 'About', path: '/', fragment: 'about' },
    { label: 'Menus', path: '/carte' },
    { label: 'Services', path: '/', fragment: 'services' },
    { label: 'Reserve', path: '/restaurant' },
    { label: 'Gallery', path: '/', fragment: 'gallery' },
    { label: 'Testimonials', path: '/', fragment: 'testimonials' },
  ];

  // Le listener n'est exécuté que dans le navigateur, donc compatible SSR
  @HostListener('window:scroll')
  onScroll(): void {
    this.scrolled.set(window.scrollY > 40);
  }

  toggleMenu(): void {
    this.menuOpen.update((v) => !v);
  }

  closeMenu(): void {
    this.menuOpen.set(false);
  }
}