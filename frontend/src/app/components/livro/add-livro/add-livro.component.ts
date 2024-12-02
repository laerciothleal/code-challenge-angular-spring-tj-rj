import { Component, OnInit } from '@angular/core';
import { Livro } from '../../../models/livro.model';
import { Autor } from '../../../models/autor.model';
import { Assunto } from '../../../models/assunto.model';
import { LivroService } from '../../../services/livro.service';
import { AutorService } from '../../../services/autor.service';
import { AssuntoService } from '../../../services/assunto.service';

@Component({
  selector: 'app-add-livro',
  templateUrl: './add-livro.component.html',
  styleUrls: ['./add-livro.component.css'],
})
export class AddLivroComponent implements OnInit {
  livro: Livro = {
    titulo: '',
    editora: '',
    edicao: 0,
    anoPublicacao: '',
    valor: 0,
  };

  autores: Autor[] = []; // Lista de autores para o select
  assuntos: Assunto[] = []; // Lista de assuntos para o select
  livroRequest = {
    autoresIds: [] as number[], // IDs dos autores selecionados
    assuntosIds: [] as number[], // IDs dos assuntos selecionados
  };

  submitted = false;
  message = '';

  constructor(
    private livroService: LivroService,
    private autorService: AutorService,
    private assuntoService: AssuntoService
  ) {}

  ngOnInit(): void {
    this.retrieveAutores();
    this.retrieveAssuntos();
  }

  retrieveAutores(): void {
    this.autorService.getAll().subscribe({
      next: (data) => {
        this.autores = data;
        console.log(data);
      },
      error: (err) => console.error(err),
    });
  }

  retrieveAssuntos(): void {
    this.assuntoService.getAll().subscribe({
      next: (data) => {
        this.assuntos = data;
        console.log(data);
      },
      error: (err) => console.error(err),
    });
  }

  saveLivro(): void {
    const data = {
      ...this.livro,
      autoresIds: this.livroRequest.autoresIds,
      assuntosIds: this.livroRequest.assuntosIds,
    };

    this.livroService.create(data).subscribe({
      next: (res) => {
        console.log(res);
        this.message = 'Livro criado com sucesso!';
        this.submitted = true;
      },
      error: (e) => console.error(e),
    });
  }

  newLivro(): void {
    this.message = '';
    this.submitted = false;
    this.livro = {
      titulo: '',
      editora: '',
      edicao: 0,
      anoPublicacao: '',
      valor: 0,
    };
    this.livroRequest = {
      autoresIds: [],
      assuntosIds: [],
    };
  }
}
