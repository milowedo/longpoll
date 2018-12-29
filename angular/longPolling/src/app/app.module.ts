import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { SubscribeComponentComponent } from './subscribe-component/subscribe-component.component';
import {RouterModule} from '@angular/router';
import { GetComponentComponent } from './get-component/get-component.component';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import {HttpResponseInterceptor} from './response-interceptor';
import {DEFAULT_TIMEOUT, HttpRequestInterceptor} from './request-interceptor';
import {TokenStorage} from './TokenStorage';
import { ChangeComponentComponent } from './change-component/change-component.component';

@NgModule({
  declarations: [
    AppComponent,
    SubscribeComponentComponent,
    GetComponentComponent,
    ChangeComponentComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    RouterModule.forRoot([
      {path: '', component: AppComponent},
      {path: 'first', component: SubscribeComponentComponent},
      {path: 'second', component: GetComponentComponent},
    ])
  ],
  providers: [
    TokenStorage,
    [{ provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true }],
    [{ provide: DEFAULT_TIMEOUT, useValue: 30000 }],
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpResponseInterceptor,
      multi: true
    },

  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
