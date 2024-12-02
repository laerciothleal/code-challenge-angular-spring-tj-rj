import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AddLivroComponent } from './components/livro/add-livro/add-livro.component';
import { LivroDetailsComponent } from './components/livro/livro-details/livro-details.component';
import { LivroListComponent } from './components/livro/livro-list/livro-list.component';

import { AddAssuntoComponent } from './components/assunto/add-assunto/add-assunto.component';
import { AssuntoDetailsComponent } from './components/assunto/assunto-details/assunto-details.component';
import { AssuntoListComponent } from './components/assunto/assunto-list/assunto-list.component';

import { AutorListComponent } from './components/autor/autor-list/autor-list.component';
import { AutorDetailsComponent } from './components/autor/autor-details/autor-details.component';
import { AddAutorComponent } from './components/autor/add-autor/add-autor.component';

import { NgxMaskDirective, provideNgxMask } from 'ngx-mask';

import { RelatorioComponent } from './components/relatorio/relatorio.component';

// Importações para configuração de localização
import { registerLocaleData } from '@angular/common';
import localePt from '@angular/common/locales/pt';
import { LOCALE_ID } from '@angular/core';

// Registrar a localização pt-BR
registerLocaleData(localePt, 'pt-BR');

@NgModule({
  declarations: [
    AppComponent,
    AddLivroComponent,
    LivroDetailsComponent,
    LivroListComponent,
    AddAssuntoComponent,
    AssuntoDetailsComponent,
    AssuntoListComponent,
    AutorListComponent,
    AutorDetailsComponent,
    AddAutorComponent,
    RelatorioComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgxMaskDirective, // Diretiva para usar máscaras
  ],
  providers: [
    provideNgxMask(), // Provedor necessário para o ngx-mask
    { provide: LOCALE_ID, useValue: 'pt-BR' }, // Configuração de localização
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
