import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JwtTestService } from '../../services/jwt-test.service';
@Component({
  imports: [CommonModule],
  standalone: true,
  selector: 'app-jwt-test',
  styleUrl: './jwt-test.css',
  templateUrl: './jwt-test.html',
})
export class JwtTest {
  result = '';
  success = false;

  constructor(private jwtService: JwtTestService) {}

  testValidToken(): void {
    this.jwtService.testValidToken().subscribe({
      next: (response) => this.showResult(response, true),
      error: (error) => this.showResult(`Token refusé : HTTP ${error.status}`, false),
    });
  }

  testInvalidToken(): void {
    this.jwtService.testInvalidToken().subscribe({
      next: () => this.showResult('Échec : le serveur a accepté un token invalide.', false),
      error: (error) =>
        this.showResult(
          `Test réussi : token invalide refusé (HTTP ${error.status}).`,
          error.status === 401 || error.status === 403,
        ),
    });
  }

  testWithoutToken(): void {
    this.jwtService.testWithoutToken().subscribe({
      next: () => this.showResult('Échec : le serveur a autorisé une requête sans token.', false),
      error: (error) =>
        this.showResult(
          `Test réussi : accès refusé sans token (HTTP ${error.status}).`,
          error.status === 401 || error.status === 403,
        ),
    });
  }

  private showResult(message: string, success: boolean): void {
    this.result = message;
    this.success = success;
  }
}
