import { Component, OnInit } from '@angular/core';
import { Assunto } from '../../../models/assunto.model';
import { AssuntoService } from '../../../services/assunto.service';

@Component({
  selector: 'app-assunto-list',
  templateUrl: './assunto-list.component.html',
  styleUrls: ['./assunto-list.component.css'],
})
export class AssuntoListComponent implements OnInit {
  
  assuntos?: Assunto[];
  currentAssunto: Assunto = {};
  currentIndex = -1;
  name = '';

  constructor(private assuntoService: AssuntoService) {}

  ngOnInit(): void {
    this.retrieveAssuntos();
  }

  retrieveAssuntos(): void {
    this.assuntoService.getAll().subscribe({
      next: (data) => {
        this.assuntos = data;
        console.log(data);
      },
      error: (e) => console.error(e)
    });
  }

  refreshList(): void {
    this.retrieveAssuntos();
    this.currentAssunto = {};
    this.currentIndex = -1;
  }

  setActiveAssunto(assunto: Assunto, index: number): void {
    this.currentAssunto = assunto;
    this.currentIndex = index;
  }

  trackByCodAs(index: number, assunto: Assunto): number {
    return assunto.codas;
  }

}
