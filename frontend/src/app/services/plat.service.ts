import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface PlatDto {
  id: number;
  nom: string;
  description: string | null;
  prix: number | null;
  disponible: boolean | null;
}

@Injectable({ providedIn: 'root' })
export class PlatService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8081/api/plats';

  getPlatsByCategory(categoryId: number): Observable<PlatDto[]> {
    return this.http.get<PlatDto[]>(this.apiUrl, { params: { categoryId } });
  }
}
