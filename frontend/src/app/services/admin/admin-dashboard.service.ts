import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DashboardStats } from '../../core/Model/dashboard-stats';

@Injectable({
  providedIn: 'root',
})
export class AdminDashboardService {
  private http = inject(HttpClient);

  private url = 'http://localhost:8081/api/admin/dashboard/stats';

  getStats(): Observable<DashboardStats> {
    return this.http.get<DashboardStats>(this.url);
  }
}
