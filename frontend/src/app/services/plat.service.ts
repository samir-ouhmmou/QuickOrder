import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api';

export interface PlatDto {
  id: number;
  nom: string;
  description: string | null;
  prix: number | null;
  disponible: boolean | null;
  categorieId: number | null;
  imageUrl: string | null;
}

export type PlatRequest = Omit<PlatDto, 'id'> & { categorieId: number };

@Injectable({ providedIn: 'root' })
export class PlatService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${API_BASE_URL}/plats`;

  getPlatsByCategory(categoryId: number): Observable<PlatDto[]> {
    return this.http.get<PlatDto[]>(`${this.apiUrl}/categorie/${categoryId}`);
  }

  getPlats(): Observable<PlatDto[]> {
    return this.http.get<PlatDto[]>(this.apiUrl);
  }

  createPlat(plat: PlatRequest): Observable<PlatDto> {
    return this.http.post<PlatDto>(this.apiUrl, plat);
  }

  updatePlat(id: number, plat: PlatRequest): Observable<PlatDto> {
    return this.http.put<PlatDto>(`${this.apiUrl}/${id}`, plat);
  }

  deletePlat(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
