import { Component, OnInit, inject, signal } from '@angular/core';
import { CategoryDto, CategoryService } from '../../services/category.service';

interface CategoryCard {
  id: number;
  title: string;
  count: number;
  description: string;
  image: string;
}

@Component({
  selector: 'app-carte',
  standalone: true,
  templateUrl: './carte.html',
  styleUrl: './carte.css',
})
export class Carte implements OnInit {
  private readonly categoryService = inject(CategoryService);

  categories = signal<CategoryCard[]>([]);
  loading = signal(true);
  errorMessage = signal('');

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe({
      next: (categories) => {
        this.categories.set(categories.map((category) => this.toCard(category)));
        this.loading.set(false);
      },
      error: () => {
        this.loading.set(false);
        this.errorMessage.set('Impossible de charger les catégories. Veuillez réessayer plus tard.');
      },
    });
  }

  private toCard(category: CategoryDto): CategoryCard {
    return {
      id: category.id,
      title: category.nom,
      count: category.nombresPlats ?? 0,
      description: category.description ?? '',
      image: this.imageFor(category.nom),
    };
  }

  private imageFor(name: string): string {
    const normalizedName = name.toLocaleLowerCase('fr');

    if (normalizedName.includes('tajine')) return 'images/plats/tajine.png';
    if (normalizedName.includes('couscous')) return 'images/plats/conscous.png';
    if (normalizedName.includes('pastilla')) return 'images/plats/pastilla.png';
    if (normalizedName.includes('boisson')) return 'images/plats/na3na3.png';

    return 'images/maroc.png';
  }
}