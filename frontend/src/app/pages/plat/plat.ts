import { Component, OnInit, inject, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { PlatDto, PlatService } from '../../services/plat.service';
import { Auth } from '../../services/auth/auth';

@Component({
  selector: 'app-plats',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './plat.html',
  styleUrl: './plat.css',
})
export class Plats implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly platService = inject(PlatService);
  private readonly auth = inject(Auth);

  readonly plats = signal<PlatDto[]>([]);
  readonly panier = signal<PlatDto[]>([]);
  readonly categoryName = signal('Plats');
  readonly loading = signal(true);
  readonly errorMessage = signal('');

  ajouter(plat: PlatDto): void {
    if (!this.auth.getToken()) {
      void this.router.navigate(['/login'], {
        queryParams: { returnUrl: this.router.url },
      });
      return;
    }

    this.panier.update((items) => [...items, plat]);
  }

  imageFor(name: string): string {
    const normalizedName = name.toLocaleLowerCase('fr').normalize('NFD').replace(/[\u0300-\u036f]/g, '');

    if (normalizedName.includes('tajine')) return 'images/plats/tajine.png';
    if (normalizedName.includes('couscous')) return 'images/plats/conscous.png';
    if (normalizedName.includes('pastilla') || normalizedName.includes('briouate')) {
      return 'images/plats/pastilla.png';
    }
    if (normalizedName.includes('chebakia')) return 'images/plats/pastilla.png';
    if (normalizedName.includes('the') || normalizedName.includes('jus')) {
      return 'images/plats/na3na3.png';
    }

    return 'images/hero.jpg';
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      const categoryId = Number(params.get('categoryId'));
      this.categoryName.set(this.route.snapshot.queryParamMap.get('category') ?? 'Plats');

      if (!Number.isInteger(categoryId) || categoryId <= 0) {
        this.loading.set(false);
        this.errorMessage.set('Cette catégorie est invalide.');
        return;
      }

      this.loading.set(true);
      this.errorMessage.set('');
      this.platService.getPlatsByCategory(categoryId).subscribe({
        next: (plats) => {
          this.plats.set(plats);
          this.loading.set(false);
        },
        error: (error: HttpErrorResponse) => {
          this.loading.set(false);
          this.errorMessage.set('Impossible de charger les plats. Veuillez réessayer plus tard.');
          console.error('Erreur lors du chargement des plats de la catégorie.', error);
        },
      });
    });
  }
}