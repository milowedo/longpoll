import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import {HttpResponseInterceptor} from './response-interceptor';
import {DEFAULT_TIMEOUT, HttpRequestInterceptor} from './request-interceptor';
import {TokenStorage} from './TokenStorage';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import { HeaderComponent } from './header/header.component';
import {DropdownDirective} from './directives/dropdown.directive';

import { GetEmployeeComponent } from './employee/get-employee/get-employee.component';
import { SubscribeEmployeeComponent } from './employee/subscribe-employee/subscribe-employee.component';
import { ChangeEmployeeComponent } from './employee/change-employee/change-employee.component';
import { EmployeeComponent } from './employee/employee.component';
import {AppRoutingModule} from './app.routing';
import { TicketComponent } from './ticket/ticket.component';
import { ChangeTicketComponent } from './ticket/change-ticket/change-ticket.component';
import { SubscribeTicketComponent } from './ticket/subscribe-ticket/subscribe-ticket.component';
import { TicketListComponent } from './ticket/ticket-list/ticket-list.component';

@NgModule({
  declarations: [
    AppComponent,
    SubscribeEmployeeComponent,
    GetEmployeeComponent,
    ChangeEmployeeComponent,
    HeaderComponent,
    DropdownDirective,
    EmployeeComponent,
    TicketComponent,
    ChangeTicketComponent,
    SubscribeTicketComponent,
    TicketListComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule
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
