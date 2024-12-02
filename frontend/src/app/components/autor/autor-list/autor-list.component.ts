import { Component, OnInit } from '@angular/core';
import { Autor } from '../../../models/autor.model';
import { AutorService } from '../../../services/autor.service';

@Component({
  selector: 'app-autor-list',
  templateUrl: './autor-list.component.html',
  styleUrls: ['./autor-list.component.css'],
})
export class AutorListComponent implements OnInit {
  autores?: Autor[];
  currentAutor: Autor = {};
  currentIndex = -1;
  name = '';

  constructor(private autorService: AutorService) {}

  ngOnInit(): void {
    this.retrieveAutors();
  }

  retrieveAutors(): void {
    this.autorService.getAll().subscribe({
      next: (data) => {
        this.autores = data;
        console.log(data);
      },
      error: (e) => console.error(e)
    });
  }

  refreshList(): void {
    this.retrieveAutors();
    this.currentAutor = {};
    this.currentIndex = -1;
  }

  setActiveAutor(autor: Autor, index: number): void {
    this.currentAutor = autor;
    this.currentIndex = index;
  }

  trackByCodau(index: number, autor: Autor): number {
    return autor.codau!;
  }

}
