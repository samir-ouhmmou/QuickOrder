import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api';

export interface CategoryDto {
  id: number;
  nom: string;
  description: string | null;
  active: boolean | null;
  disponible: boolean | null;
  nombresPlats: number | null;
}

@Injectable({ providedIn: 'root' })
export class CategoryService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${API_BASE_URL}/category`;

  getCategories(): Observable<CategoryDto[]> {
    return this.http.get<CategoryDto[]>(this.apiUrl);
  }
}
