import { Component, OnInit, inject, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { PlatDto, PlatService } from '../../services/plat.service';

@Component({
  selector: 'app-plats',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './plat.html',
  styleUrl: './plat.css',
})
export class Plats implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly platService = inject(PlatService);

  readonly plats = signal<PlatDto[]>([]);
  readonly panier = signal<PlatDto[]>([]);
  readonly categoryName = signal('Plats');
  readonly loading = signal(true);
  readonly errorMessage = signal('');

  ajouter(plat: PlatDto): void {
    this.panier.update((items) => [...items, plat]);
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