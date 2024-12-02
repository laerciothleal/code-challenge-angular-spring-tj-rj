import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Livro } from '../../../models/livro.model';
import { Autor } from '../../../models/autor.model';
import { Assunto } from '../../../models/assunto.model';
import { LivroService } from '../../../services/livro.service';
import { AutorService } from '../../../services/autor.service';
import { AssuntoService } from '../../../services/assunto.service';

@Component({
  selector: 'app-livro-details',
  templateUrl: './livro-details.component.html',
  styleUrls: ['./livro-details.component.css'],
})
export class LivroDetailsComponent implements OnInit {
  @Input() viewMode = false;

  @Input() currentLivro: Livro = {
    titulo: '',
    editora: '',
    edicao: 0,
    anoPublicacao: '',
    valor: 0,
  };

  autores: Autor[] = []; // Lista de autores
  assuntos: Assunto[] = []; // Lista de assuntos
  livroRequest = {
    autoresIds: [] as number[], // IDs dos autores selecionados
    assuntosIds: [] as number[], // IDs dos assuntos selecionados
  };

  message = '';

  constructor(
    private livroService: LivroService,
    private autorService: AutorService,
    private assuntoService: AssuntoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (!this.viewMode) {
      this.message = '';
      const livroId = this.route.snapshot.params['id'];
      this.retrieveAutores(() => {
        this.retrieveAssuntos(() => {
          this.getLivro(livroId);
        });
      });
    }
  }

  getLivro(id: number): void {
    this.livroService.get(id).subscribe({
      next: (data) => {
        this.currentLivro = data;
  
        // Mapeia os objetos para arrays de IDs
        this.livroRequest.autoresIds = data.autores?.map((autor: any) => autor.codAu) || [];
        this.livroRequest.assuntosIds = data.assuntos?.map((assunto: any) => assunto.codigoAs) || [];
  
        console.log("Livro carregado:", data);
        console.log("Autores Ids:", this.livroRequest.autoresIds);
        console.log("Assuntos Ids:", this.livroRequest.assuntosIds);
      },
      error: (e) => console.error("Erro ao carregar o livro:", e),
    });
  }
  
  
  // Preencher autores já selecionados
  preselectAutores(): void {
    this.livroRequest.autoresIds.forEach((id) => {
      const autor = this.autores.find((a) => a.codau === id);
      if (autor) autor['selected'] = true;
    });
  }
  
  // Preencher assuntos já selecionados
  preselectAssuntos(): void {
    this.livroRequest.assuntosIds.forEach((id) => {
      const assunto = this.assuntos.find((a) => a.codas === id);
      if (assunto) assunto['selected'] = true;
    });
  }

  retrieveAutores(callback?: () => void): void {
    this.autorService.getAll().subscribe({
      next: (data) => {
        this.autores = data;
        console.log("Autores carregados:", data);
        if (callback) callback();
      },
      error: (e) => {
        console.error("Erro ao carregar autores:", e);
        if (callback) callback();
      },
    });
  }

  retrieveAssuntos(callback?: () => void): void {
    this.assuntoService.getAll().subscribe({
      next: (data) => {
        this.assuntos = data;
        console.log("Assuntos carregados:", data);
        if (callback) callback();
      },
      error: (e) => {
        console.error("Erro ao carregar assuntos:", e);
        if (callback) callback();
      },
    });
  }

  updateLivro(): void {
    this.message = '';
    const updatedLivro = {
      ...this.currentLivro,
      autoresIds: this.livroRequest.autoresIds,
      assuntosIds: this.livroRequest.assuntosIds,
    };
  
    this.livroService.update(this.currentLivro.codL, updatedLivro).subscribe({
      next: (res) => {
        console.log(res);
        this.message = 'Livro atualizado com sucesso!';
      },
      error: (e) => console.error(e),
    });
  }
  
  deleteLivro(): void {
    this.livroService.delete(this.currentLivro.codL).subscribe({
      next: () => {
        this.router.navigate(['/livro']);
      },
      error: (e) => console.error(e),
    });
  }
}
