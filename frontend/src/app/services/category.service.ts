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
  imageUrl: string | null;
}

export type CategoryRequest = Omit<CategoryDto, 'id' | 'nombresPlats'>;

@Injectable({ providedIn: 'root' })
export class CategoryService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${API_BASE_URL}/category`;

  getCategories(): Observable<CategoryDto[]> {
    return this.http.get<CategoryDto[]>(this.apiUrl);
  }

  createCategory(category: CategoryRequest): Observable<CategoryDto> {
    return this.http.post<CategoryDto>(this.apiUrl, category);
  }

  updateCategory(id: number, category: CategoryRequest): Observable<CategoryDto> {
    return this.http.put<CategoryDto>(`${this.apiUrl}/${id}`, category);
  }

  deleteCategory(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
