import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LivroListComponent } from './components/livro/livro-list/livro-list.component';
import { LivroDetailsComponent } from './components/livro/livro-details/livro-details.component';
import { AddLivroComponent } from './components/livro/add-livro/add-livro.component';

import { AssuntoListComponent } from './components/assunto/assunto-list/assunto-list.component';
import { AssuntoDetailsComponent } from './components/assunto/assunto-details/assunto-details.component';
import { AddAssuntoComponent } from './components/assunto/add-assunto/add-assunto.component';


import { AutorListComponent } from './components/autor/autor-list/autor-list.component';
import { AutorDetailsComponent } from './components/autor/autor-details/autor-details.component';
import { AddAutorComponent } from './components/autor/add-autor/add-autor.component';

import { RelatorioComponent } from './components/relatorio/relatorio.component';


const routes: Routes = [
  { path: '', redirectTo: 'livro', pathMatch: 'full' },
  { path: 'livro', component: LivroListComponent },
  { path: 'livro/:id', component: LivroDetailsComponent },
  { path: 'add/livro', component: AddLivroComponent },
  { path: 'assunto', component: AssuntoListComponent },
  { path: 'assunto/:id', component: AssuntoDetailsComponent },
  { path: 'add/assunto', component: AddAssuntoComponent },
  { path: 'autor', component: AutorListComponent },
  { path: 'autor/:id', component: AutorDetailsComponent },
  { path: 'add/autor', component: AddAutorComponent },
  { path: 'relatorio/livros-por-autor', component: RelatorioComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
