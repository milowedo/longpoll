import { Component, OnInit } from '@angular/core';
import {Ticket} from '../../entities/ticket.model';
import {TicketService} from '../../services/ticket.service';

@Component({
  selector: 'app-ticket-list',
  templateUrl: './ticket-list.component.html',
  providers: [TicketService],
})
export class TicketListComponent implements OnInit{

  tickets: Ticket[];

  constructor(private ticketService: TicketService) {}

  ngOnInit(): void {
    this.ticketService.fetchAllTickets();
    this.tickets = this.ticketService.getTickets();
    this.ticketService.ticketsChanged.subscribe(
      (newTickets : Ticket[]) =>
        this.tickets = newTickets
    )
  }

}
