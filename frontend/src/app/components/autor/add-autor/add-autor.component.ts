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
      const data = {
        nome: this.autor.nome
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
