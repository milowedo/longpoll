import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {EmployeeComponent} from './employee/employee.component';
import {TicketComponent} from './ticket/ticket.component';
import {TicketListComponent} from './ticket/ticket-list/ticket-list.component';

const appRoutes = [
  {path: 'employees', component: EmployeeComponent },
  {path: 'tickets', component: TicketListComponent},
  {path: '', redirectTo: '/employees', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
