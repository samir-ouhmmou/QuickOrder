import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { CategoryDto, CategoryRequest, CategoryService } from '../../services/category.service';
import { PlatDto, PlatRequest, PlatService } from '../../services/plat.service';

type ManagerSection = 'categories' | 'plats';
type Feedback = { kind: 'success' | 'error'; text: string };
type PendingDelete = { kind: ManagerSection; id: number; name: string };

@Component({
  selector: 'app-gerant',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './gerant.html',
  styleUrl: './gerant.css',
})
export class Gerant implements OnInit {
  private readonly fb = inject(FormBuilder);
  private readonly categoryService = inject(CategoryService);
  private readonly platService = inject(PlatService);

  readonly section = signal<ManagerSection>('categories');
  readonly categories = signal<CategoryDto[]>([]);
  readonly plats = signal<PlatDto[]>([]);
  readonly loadingCategories = signal(true);
  readonly loadingPlats = signal(true);
  readonly feedback = signal<Feedback | null>(null);
  readonly search = signal('');
  readonly categoryFilter = signal<number | null>(null);
  readonly categoryDialogOpen = signal(false);
  readonly platDialogOpen = signal(false);
  readonly categorySubmitting = signal(false);
  readonly platSubmitting = signal(false);
  readonly editingCategoryId = signal<number | null>(null);
  readonly editingPlatId = signal<number | null>(null);
  readonly pendingDelete = signal<PendingDelete | null>(null);
  readonly categoryImageUrl = signal<string | null>(null);
  readonly platImageUrl = signal<string | null>(null);

  readonly categoryForm = this.fb.nonNullable.group({
    nom: ['', [Validators.required, Validators.maxLength(80)]],
    description: ['', [Validators.maxLength(500)]],
    active: [true],
    disponible: [true],
  });

  readonly platForm = this.fb.nonNullable.group({
    nom: ['', [Validators.required, Validators.maxLength(100)]],
    description: ['', [Validators.maxLength(500)]],
    prix: [0, [Validators.required, Validators.min(0.01)]],
    categorieId: [0, [Validators.required, Validators.min(1)]],
    disponible: [true],
  });

  readonly filteredCategories = computed(() => {
    const term = this.search().trim().toLocaleLowerCase('fr');
    return this.categories().filter((category) =>
      `${category.nom} ${category.description ?? ''}`.toLocaleLowerCase('fr').includes(term),
    );
  });

  readonly availablePlats = computed(
    () => this.plats().filter((plat) => plat.disponible !== false).length,
  );

  readonly filteredPlats = computed(() => {
    const term = this.search().trim().toLocaleLowerCase('fr');
    const categoryId = this.categoryFilter();
    return this.plats().filter((plat) => {
      const category = this.categoryName(plat.categorieId);
      const matchesTerm = `${plat.nom} ${plat.description ?? ''} ${category}`
        .toLocaleLowerCase('fr')
        .includes(term);
      return matchesTerm && (categoryId === null || plat.categorieId === categoryId);
    });
  });

  ngOnInit(): void {
    this.loadCategories();
    this.loadPlats();
  }

  switchSection(section: ManagerSection): void {
    this.section.set(section);
    this.search.set('');
    this.feedback.set(null);
  }

  categoryName(id: number | null): string {
    if (id === null) {
      return 'Catégorie inconnue';
    }
    return this.categories().find((category) => category.id === id)?.nom ?? 'Catégorie inconnue';
  }

  openNewCategory(): void {
    this.editingCategoryId.set(null);
    this.categoryForm.reset({ nom: '', description: '', active: true, disponible: true });
    this.categoryImageUrl.set(null);
    this.categoryDialogOpen.set(true);
  }

  openEditCategory(category: CategoryDto): void {
    this.editingCategoryId.set(category.id);
    this.categoryForm.reset({
      nom: category.nom,
      description: category.description ?? '',
      active: category.active ?? true,
      disponible: category.disponible ?? true,
    });
    this.categoryImageUrl.set(category.imageUrl);
    this.categoryDialogOpen.set(true);
  }

  saveCategory(): void {
    this.feedback.set(null);
    if (this.categoryForm.invalid) {
      this.categoryForm.markAllAsTouched();
      return;
    }

    const request: CategoryRequest = {
      ...this.categoryForm.getRawValue(),
      imageUrl: this.categoryImageUrl(),
    };
    const id = this.editingCategoryId();
    this.categorySubmitting.set(true);
    const operation = id === null
      ? this.categoryService.createCategory(request)
      : this.categoryService.updateCategory(id, request);

    operation.subscribe({
      next: () => {
        this.categorySubmitting.set(false);
        this.categoryDialogOpen.set(false);
        this.feedback.set({
          kind: 'success',
          text: id === null ? 'Catégorie créée avec succès.' : 'Catégorie modifiée avec succès.',
        });
        this.loadCategories();
      },
      error: (error: HttpErrorResponse) => {
        this.categorySubmitting.set(false);
        this.feedback.set({ kind: 'error', text: this.errorText(error, 'Impossible d’enregistrer la catégorie.') });
      },
    });
  }

  openNewPlat(): void {
    if (this.categories().length === 0) {
      this.feedback.set({ kind: 'error', text: 'Créez d’abord une catégorie pour pouvoir ajouter un plat.' });
      return;
    }

    this.editingPlatId.set(null);
    this.platForm.reset({
      nom: '',
      description: '',
      prix: 0,
      categorieId: this.categories()[0].id,
      disponible: true,
    });
    this.platImageUrl.set(null);
    this.platDialogOpen.set(true);
  }

  openEditPlat(plat: PlatDto): void {
    this.editingPlatId.set(plat.id);
    this.platForm.reset({
      nom: plat.nom,
      description: plat.description ?? '',
      prix: plat.prix ?? 0,
      categorieId: plat.categorieId ?? this.categories()[0]?.id ?? 0,
      disponible: plat.disponible ?? true,
    });
    this.platImageUrl.set(plat.imageUrl);
    this.platDialogOpen.set(true);
  }

  savePlat(): void {
    this.feedback.set(null);
    if (this.platForm.invalid) {
      this.platForm.markAllAsTouched();
      return;
    }

    const request: PlatRequest = {
      ...this.platForm.getRawValue(),
      imageUrl: this.platImageUrl(),
    };
    const id = this.editingPlatId();
    this.platSubmitting.set(true);
    const operation = id === null
      ? this.platService.createPlat(request)
      : this.platService.updatePlat(id, request);

    operation.subscribe({
      next: () => {
        this.platSubmitting.set(false);
        this.platDialogOpen.set(false);
        this.feedback.set({
          kind: 'success',
          text: id === null ? 'Plat créé avec succès.' : 'Plat modifié avec succès.',
        });
        this.loadPlats();
        this.loadCategories();
      },
      error: (error: HttpErrorResponse) => {
        this.platSubmitting.set(false);
        this.feedback.set({ kind: 'error', text: this.errorText(error, 'Impossible d’enregistrer le plat.') });
      },
    });
  }

  askDelete(kind: ManagerSection, id: number, name: string): void {
    this.pendingDelete.set({ kind, id, name });
  }

  confirmDelete(): void {
    const pending = this.pendingDelete();
    if (!pending) {
      return;
    }

    const request = pending.kind === 'categories'
      ? this.categoryService.deleteCategory(pending.id)
      : this.platService.deletePlat(pending.id);

    request.subscribe({
      next: () => {
        this.pendingDelete.set(null);
        this.feedback.set({
          kind: 'success',
          text: pending.kind === 'categories' ? 'Catégorie supprimée.' : 'Plat supprimé.',
        });
        if (pending.kind === 'categories') {
          this.loadCategories();
        } else {
          this.loadPlats();
          this.loadCategories();
        }
      },
      error: (error: HttpErrorResponse) => {
        this.pendingDelete.set(null);
        this.feedback.set({ kind: 'error', text: this.errorText(error, 'La suppression a échoué.') });
      },
    });
  }

  dismissFeedback(): void {
    this.feedback.set(null);
  }

  onCategoryImageSelected(event: Event): void {
    this.readImage(event, (imageUrl) => this.categoryImageUrl.set(imageUrl));
  }

  onPlatImageSelected(event: Event): void {
    this.readImage(event, (imageUrl) => this.platImageUrl.set(imageUrl));
  }

  private readImage(event: Event, update: (imageUrl: string) => void): void {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0];
    if (!file) {
      return;
    }
    if (!['image/png', 'image/jpeg', 'image/webp'].includes(file.type)) {
      this.feedback.set({ kind: 'error', text: 'Sélectionnez une image PNG, JPG ou WebP.' });
      input.value = '';
      return;
    }
    if (file.size > 4 * 1024 * 1024) {
      this.feedback.set({ kind: 'error', text: 'L’image ne doit pas dépasser 4 Mo.' });
      input.value = '';
      return;
    }

    const reader = new FileReader();
    reader.onload = () => update(String(reader.result));
    reader.readAsDataURL(file);
  }

  private loadCategories(): void {
    this.loadingCategories.set(true);
    this.categoryService.getCategories().subscribe({
      next: (categories) => {
        this.categories.set(categories);
        this.loadingCategories.set(false);
      },
      error: (error: HttpErrorResponse) => {
        this.loadingCategories.set(false);
        this.feedback.set({ kind: 'error', text: this.errorText(error, 'Impossible de charger les catégories.') });
      },
    });
  }

  private loadPlats(): void {
    this.loadingPlats.set(true);
    this.platService.getPlats().subscribe({
      next: (plats) => {
        this.plats.set(plats);
        this.loadingPlats.set(false);
      },
      error: (error: HttpErrorResponse) => {
        this.loadingPlats.set(false);
        this.feedback.set({ kind: 'error', text: this.errorText(error, 'Impossible de charger les plats.') });
      },
    });
  }

  private errorText(error: HttpErrorResponse, fallback: string): string {
    if (error.status === 0) {
      return 'Le serveur est injoignable. Vérifiez votre connexion puis réessayez.';
    }
    if (error.status === 401 || error.status === 403) {
      return 'Votre session a expiré ou votre compte n’est pas autorisé à effectuer cette action.';
    }
    if (typeof error.error?.message === 'string') {
      return error.error.message;
    }
    return fallback;
  }
}
