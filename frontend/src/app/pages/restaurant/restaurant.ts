import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { forkJoin, map } from 'rxjs';
import { CategoryDto, CategoryService } from '../../services/category.service';
import { Auth } from '../../services/auth/auth';
import { CommandeResponseDto, CommandeService, TableRestaurantDto } from '../../services/commande.service';
import { PlatDto, PlatService } from '../../services/plat.service';

interface CategoryMenu extends CategoryDto {
  plats: PlatDto[];
}

interface LignePanier {
  plat: PlatDto;
  quantite: number;
}

@Component({
  selector: 'app-restaurant',
  imports: [RouterLink],
  templateUrl: './restaurant.html',
  styleUrl: './restaurant.css',
})
export class Restaurant implements OnInit {
  private readonly auth = inject(Auth);
  private readonly categoryService = inject(CategoryService);
  private readonly commandeService = inject(CommandeService);
  private readonly platService = inject(PlatService);
  private readonly router = inject(Router);

  readonly authenticated = signal(false);
  readonly categories = signal<CategoryMenu[]>([]);
  readonly panier = signal<LignePanier[]>([]);
  readonly tables = signal<TableRestaurantDto[]>([]);
  readonly tableSelectionnee = signal<number | null>(null);
  readonly chargementMenu = signal(true);
  readonly chargementTables = signal(false);
  readonly envoiEnCours = signal(false);
  readonly erreurMenu = signal('');
  readonly erreurTables = signal('');
  readonly erreurCommande = signal('');
  readonly confirmation = signal<CommandeResponseDto | null>(null);
  readonly total = computed(() =>
    this.panier().reduce((somme, ligne) => somme + (ligne.plat.prix ?? 0) * ligne.quantite, 0),
  );

  ngOnInit(): void {
    this.authenticated.set(Boolean(this.auth.getToken()));
    this.chargerMenu();
    if (this.authenticated()) {
      this.chargerTables();
    }
  }

  ajouter(plat: PlatDto): void {
    this.panier.update((lignes) => {
      const ligneExistante = lignes.find((ligne) => ligne.plat.id === plat.id);
      if (ligneExistante) {
        return lignes.map((ligne) =>
          ligne.plat.id === plat.id ? { ...ligne, quantite: ligne.quantite + 1 } : ligne,
        );
      }
      return [...lignes, { plat, quantite: 1 }];
    });
    this.confirmation.set(null);
    this.erreurCommande.set('');
  }

  diminuer(platId: number): void {
    this.panier.update((lignes) =>
      lignes
        .map((ligne) =>
          ligne.plat.id === platId ? { ...ligne, quantite: ligne.quantite - 1 } : ligne,
        )
        .filter((ligne) => ligne.quantite > 0),
    );
    this.confirmation.set(null);
    this.erreurCommande.set('');
  }

  quantite(platId: number): number {
    return this.panier().find((ligne) => ligne.plat.id === platId)?.quantite ?? 0;
  }

  retirer(platId: number): void {
    this.panier.update((lignes) => lignes.filter((ligne) => ligne.plat.id !== platId));
    this.confirmation.set(null);
    this.erreurCommande.set('');
  }

  choisirTable(event: Event): void {
    const valeur = (event.target as HTMLSelectElement).value;
    this.tableSelectionnee.set(valeur ? Number(valeur) : null);
  }

  formatPrix(prix: number | null): string {
    return new Intl.NumberFormat('fr-MA', {
      style: 'currency',
      currency: 'MAD',
    }).format(prix ?? 0);
  }

  creerCommande(): void {
    this.erreurCommande.set('');
    if (!this.authenticated()) {
      void this.router.navigate(['/login'], {
        queryParams: { returnUrl: '/restaurant' },
      });
      return;
    }
    if (this.panier().length === 0) {
      this.erreurCommande.set('Ajoutez au moins un plat à votre commande.');
      return;
    }

    this.envoiEnCours.set(true);
    const tableId = this.tableSelectionnee();
    this.commandeService
      .creerCommande({
        commentaire: null,
        tableRestaurant: tableId === null ? null : { id: tableId },
        ligneCommandes: this.panier().map((ligne) => ({
          quantite: ligne.quantite,
          plat: { id: ligne.plat.id },
        })),
      })
      .subscribe({
        next: (commande) => {
          this.confirmation.set(commande);
          this.panier.set([]);
          this.tableSelectionnee.set(null);
          this.envoiEnCours.set(false);
        },
        error: (error: HttpErrorResponse) => {
          this.erreurCommande.set(this.messageErreur(error));
          this.envoiEnCours.set(false);
        },
      });
  }

  private chargerMenu(): void {
    this.chargementMenu.set(true);
    this.erreurMenu.set('');
    this.categoryService.getCategories().subscribe({
      next: (loadedCategories) => {
        const categories = loadedCategories.filter(
          (category) => category.active !== false && category.disponible !== false,
        );
        if (categories.length === 0) {
          this.categories.set([]);
          this.chargementMenu.set(false);
          return;
        }

        forkJoin(
          categories.map((category) =>
            this.platService.getPlatsByCategory(category.id).pipe(
              map((plats) => ({
                ...category,
                plats: plats.filter((plat) => plat.disponible === true),
              })),
            ),
          ),
        ).subscribe({
          next: (menus) => {
            this.categories.set(menus);
            this.chargementMenu.set(false);
          },
          error: (error: HttpErrorResponse) => {
            console.error('Erreur lors du chargement des plats disponibles.', error);
            this.erreurMenu.set(this.messageErreur(error, 'Impossible de charger les plats disponibles.'));
            this.chargementMenu.set(false);
          },
        });
      },
      error: (error: HttpErrorResponse) => {
        console.error('Erreur lors du chargement des catégories.', error);
        this.erreurMenu.set(this.messageErreur(error, 'Impossible de charger le menu.'));
        this.chargementMenu.set(false);
      },
    });
  }

  private chargerTables(): void {
    this.chargementTables.set(true);
    this.commandeService.getTablesDisponibles().subscribe({
      next: (tables) => {
        this.tables.set(tables);
        this.chargementTables.set(false);
      },
      error: (error: HttpErrorResponse) => {
        console.error('Erreur lors du chargement des tables disponibles.', error);
        this.erreurTables.set(this.messageErreur(error, 'Impossible de charger les tables disponibles.'));
        this.chargementTables.set(false);
      },
    });
  }

  private messageErreur(error: HttpErrorResponse, fallback = 'La commande n’a pas pu être créée.'): string {
    if (error.status === 0) {
      return 'Impossible de joindre le serveur. Vérifiez que le backend est démarré.';
    }
    if (error.status === 401) {
      return 'Votre session a expiré. Connectez-vous pour continuer.';
    }
    if (error.status === 403) {
      return 'Seul un compte client peut créer une commande.';
    }

    const response = error.error as { detail?: unknown; message?: unknown } | null;
    if (typeof response?.detail === 'string') {
      return response.detail;
    }
    if (typeof response?.message === 'string') {
      return response.message;
    }
    return fallback;
  }

}
