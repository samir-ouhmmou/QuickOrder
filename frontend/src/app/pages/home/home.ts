import { Component, OnDestroy, OnInit, PLATFORM_ID, inject } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home implements OnInit, OnDestroy {
  private router = inject(Router);
  private platformId = inject(PLATFORM_ID);
  private timer?: ReturnType<typeof setTimeout>;

  // Délai avant l'affichage automatique de la carte (30 secondes)
  private readonly autoDelayMs = 30000;

  ngOnInit(): void {
    // Pas de minuteur côté serveur (SSR)
    if (!isPlatformBrowser(this.platformId)) return;

    this.timer = setTimeout(() => {
      this.router.navigate(['/carte'], { fragment: 'catalogue' });
    }, this.autoDelayMs);
  }

  ngOnDestroy(): void {
    // Annule le minuteur si l'utilisateur a cliqué sur le bouton
    clearTimeout(this.timer);
  }
}