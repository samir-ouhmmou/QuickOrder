import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { API_BASE_URL } from '../core/api';

export interface TableRestaurantDto {
  id: number;
  numero: number;
  capacite: number;
}

export interface CreateCommandeRequest {
  commentaire: string | null;
  tableRestaurant: { id: number } | null;
  ligneCommandes: Array<{
    quantite: number;
    plat: { id: number };
  }>;
}

export interface CommandeResponseDto {
  id: number;
  dateCreation: string;
  etat: string;
  montantTotal: number;
}

@Injectable({ providedIn: 'root' })
export class CommandeService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = `${API_BASE_URL}/commande`;

  getTablesDisponibles(): Observable<TableRestaurantDto[]> {
    return this.http.get<TableRestaurantDto[]>(`${API_BASE_URL}/table/disponibles`);
  }

  creerCommande(commande: CreateCommandeRequest): Observable<CommandeResponseDto> {
    return this.http.post<CommandeResponseDto>(this.apiUrl, commande);
  }
}
