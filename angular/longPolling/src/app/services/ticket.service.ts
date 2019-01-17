import {Ticket} from '../entities/ticket.model';
import {HttpClient} from '@angular/common/http';
import {EventEmitter, Injectable, OnInit} from '@angular/core';
import {Observable} from 'rxjs';

@Injectable()
export class TicketService{

  private tickets: Ticket[] = [];
  ticketsChanged = new EventEmitter<Ticket[]>();
  ticketClicked = new EventEmitter<Ticket>();

  constructor(private httpClient: HttpClient){};

  public fetchTickets(){
    this
      .httpClient
      .get<Ticket[]>("http://localhost:8080/ticket/all")
      .subscribe(
        data => this.setTickets(data)
      );
  }

  public setTickets(newSetOfTickets: Ticket[]){
    this.tickets = newSetOfTickets;
    this.ticketsChanged.emit(this.tickets.slice());
  }

  public getTickets(){
    return this.tickets;
  }
}
