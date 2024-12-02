import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Autor } from '../../../models/autor.model';
import { AutorService } from '../../../services/autor.service';

@Component({
  selector: 'app-autor-details',
  templateUrl: './autor-details.component.html',
  styleUrls: ['./autor-details.component.css'],
})
export class AutorDetailsComponent implements OnInit {
  @Input() viewMode = false;

  @Input() currentAutor: Autor = {
    nome: ''
  };
  message = '';

  constructor(
    private autorService: AutorService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {

    if (!this.viewMode) {
      this.message = '';
      this.getAutor(this.route.snapshot.params['id']);
      
    }
  }

  getAutor(id: number): void {
    this.autorService.get(id).subscribe({
      next: (data) => {
        this.currentAutor = data;
        console.log(data);
      },
      error: (e) => console.error(e),
    });
  }

  updateAutor(): void {
    this.message = '';
    this.autorService
      .update(this.currentAutor.codau, this.currentAutor)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.message = res.message
            ? res.message
            : 'Autor atualizado com sucesso!';
        },
        error: (e) => console.error(e)
      });
  }

  deleteAutor(): void {
    this.autorService
    .delete(this.currentAutor.codau).subscribe({
      next: (res) => {
        console.log(res);
        this.router.navigate(['/autor']);
      },
      error: (e) => console.error(e)
    });
  }

}
