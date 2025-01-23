import { Component, OnInit } from '@angular/core';
import { Autor } from '../../../models/autor.model';
import { AutorService } from '../../../services/autor.service';

@Component({
  selector: 'app-add-autor',
  templateUrl: './add-autor.component.html',
  styleUrls: ['./add-autor.component.css'],
})
export class AddAutorComponent implements OnInit {

  autor: Autor = {
    nome: ''
  };
  submitted = false;
  message = '';

  constructor(
    private AutorService: AutorService
  ) {}

  ngOnInit(): void {
  }

  saveAutor(): void {
    if (!this.autor.nome || this.autor.nome.trim().length < 3) {
      this.message = 'Por favor, preencha o campo Nome corretamente.';
      return;
    }
  
    const data = {
      nome: this.autor.nome.trim() // Remove espaços em branco extras
    };
  
    this.AutorService.create(data).subscribe({
      next: (res) => {
        console.log(res);
        this.message = '';
        this.submitted = true;
      },
      error: (e) => console.error(e)
    });
  }
  


  newAutor(): void {
    this.message = '';
    this.submitted = false;
    this.autor = {
      nome: ''
    };
  }
}
