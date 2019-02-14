import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {EmployeeComponent} from './employee/employee.component';
import {TicketListComponent} from './ticket/ticket-list/ticket-list.component';
import {TicketSubComponent} from './ticket-sub/ticket-sub.component';

const appRoutes = [
  {path: 'employeeSub', component: EmployeeComponent },
  {path: 'ticketSub', component: TicketSubComponent},
  {path: 'tickets', component: TicketListComponent},
  {path: '', redirectTo: '/employees', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(appRoutes)],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
