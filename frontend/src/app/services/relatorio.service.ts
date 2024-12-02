import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Relatorio {
  autorNome: string;
  livroTitulo: string;
  livroEditora: string;
  livroEdicao: number;
  livroAnoPublicacao: string;
  livroValor: number;
  livroAssuntos: string;
}

@Injectable({
  providedIn: 'root',
})
export class RelatorioService {
  private baseUrl = 'http://localhost:8080/api/relatorio';

  constructor(private http: HttpClient) {}

  getLivrosPorAutor(): Observable<Relatorio[]> {
    return this.http.get<Relatorio[]>(`${this.baseUrl}/livros-por-autor`);
  }
}
