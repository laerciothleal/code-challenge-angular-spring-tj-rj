import { Component, OnInit } from '@angular/core';
import { Assunto } from '../../../models/assunto.model';
import { AssuntoService } from '../../../services/assunto.service';

@Component({
  selector: 'app-add-assunto',
  templateUrl: './add-assunto.component.html',
  styleUrls: ['./add-assunto.component.css'],
})
export class AddAssuntoComponent implements OnInit {

  assunto: Assunto = {
    descricao: ''
  };
  submitted = false;
  message = '';

  constructor(
    private AssuntoService: AssuntoService
  ) {}

  ngOnInit(): void {
  }

  saveAssunto(): void {
    if (!this.assunto.descricao || this.assunto.descricao.trim().length < 3) {
      this.message = 'Por favor, preencha o campo Descrição corretamente.';
      return;
    }
  
    const data = {
      descricao: this.assunto.descricao.trim()
    };
  
    this.AssuntoService.create(data).subscribe({
      next: (res) => {
        console.log(res);
        this.message = '';
        this.submitted = true;
      },
      error: (e) => console.error(e)
    });
  }
  


  newAssunto(): void {
    this.message = '';
    this.submitted = false;
    this.assunto = {
      descricao: ''
    };
  }
}
