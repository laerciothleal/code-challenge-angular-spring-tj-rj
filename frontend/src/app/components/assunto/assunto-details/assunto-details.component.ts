import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Assunto } from '../../../models/assunto.model';
import { AssuntoService } from '../../../services/assunto.service';

@Component({
  selector: 'app-assunto-details',
  templateUrl: './assunto-details.component.html',
  styleUrls: ['./assunto-details.component.css'],
})
export class AssuntoDetailsComponent implements OnInit {
  @Input() viewMode = false;

  @Input() currentAssunto: Assunto = {
    descricao: ''
  };
  message = '';

  constructor(
    private assuntoService: AssuntoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {

    if (!this.viewMode) {
      this.message = '';
      this.getAssunto(this.route.snapshot.params['id']);
      
    }
  }

  getAssunto(id: number): void {
    this.assuntoService.get(id).subscribe({
      next: (data) => {
        this.currentAssunto = data;
        console.log(data);
      },
      error: (e) => console.error(e),
    });
  }

  updateAssunto(): void {
    this.message = '';
    this.assuntoService
      .update(this.currentAssunto.codas, this.currentAssunto)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.message = res.message
            ? res.message
            : 'Assunto atualizado com sucesso!';
        },
        error: (e) => console.error(e)
      });
  }

  deleteAssunto(): void {
    this.assuntoService
    .delete(this.currentAssunto.codas).subscribe({
      next: (res) => {
        console.log(res);
        this.router.navigate(['/assunto']);
      },
      error: (e) => console.error(e)
    });
  }

}
