import { Component, OnInit } from '@angular/core';
import { Livro } from '../../../models/livro.model';
import { LivroService } from '../../../services/livro.service';

@Component({
  selector: 'app-livro-list',
  templateUrl: './livro-list.component.html',
  styleUrls: ['./livro-list.component.css'],
})
export class LivroListComponent implements OnInit {
  
  livros?: Livro[];
  currentLivro: Livro = {};
  currentIndex = -1;
  name = '';

  constructor(private userService: LivroService) {}

  ngOnInit(): void {
    this.retrieveLivros();
  }

  retrieveLivros(): void {
    this.userService.getAll().subscribe({
      next: (data) => {
        this.livros = data;
        console.log(data);
      },
      error: (e) => console.error(e)
    });
  }

  refreshList(): void {
    this.retrieveLivros();
    this.currentLivro = {};
    this.currentIndex = -1;
  }

  setActiveLivro(user: Livro, index: number): void {
    this.currentLivro = user;
    this.currentIndex = index;
  }

}
