import { Component, OnInit } from '@angular/core';
import { RelatorioService, Relatorio } from '../../services/relatorio.service';

@Component({
  selector: 'app-relatorio',
  templateUrl: './relatorio.component.html',
  styleUrls: ['./relatorio.component.css'],
})
export class RelatorioComponent implements OnInit {
  relatorio: Relatorio[] = [];

  constructor(private relatorioService: RelatorioService) {}

  ngOnInit(): void {
    this.relatorioService.getLivrosPorAutor().subscribe({
      next: (data) => {
        this.relatorio = data;
        console.log(data);
      },
      error: (err) => console.error(err),
    });
  }
}
