import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
    providedIn: 'root'
})
export class JwtTestService {

    private readonly apiUrl = '/api';

    constructor(private http: HttpClient) {}

    // Tester le token enregistré
    testValidToken() {
        const token = localStorage.getItem('quickorder_token');

        const headers = new HttpHeaders({
            Authorization: `Bearer ${token}`
        });

        return this.http.get(
            `${this.apiUrl}/test/jwt`,
            { headers, responseType: 'text' }
        );
    }

    // Tester un token invalide
    testInvalidToken() {
        const headers = new HttpHeaders({
            Authorization: 'Bearer invalid.jwt.token'
        });

        return this.http.get(
            `${this.apiUrl}/test/jwt`,
            { headers, responseType: 'text' }
        );
    }

    // Tester sans token
    testWithoutToken() {
        return this.http.get(
            `${this.apiUrl}/test/jwt`,
            { responseType: 'text' }
        );
    }
}
