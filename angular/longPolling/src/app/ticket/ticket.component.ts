import {Component, Input, OnInit} from '@angular/core';
import {TicketService} from '../services/ticket.service';
import {Ticket} from '../entities/ticket.model';

@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  providers : [TicketService],
})
export class TicketComponent {

  @Input() ticket : Ticket;

  constructor(private ticketService: TicketService) { }

  ticketClicked(){
    this.ticketService.ticketClicked.emit(this.ticket);
  }
}
