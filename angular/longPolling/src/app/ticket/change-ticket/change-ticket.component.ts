import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-change-ticket',
  templateUrl: './change-ticket.component.html'
})
export class ChangeTicketComponent{

  constructor(private httpClient:HttpClient){}

  ticketId: string = '3';

  updateProfile() {
    let address: string =  'http://localhost:8080/ticket/trigger/' + this.ticketId;
    this.httpClient.get(address)
      .subscribe(
        (data: any) => {
          console.log(data);
        },
          () => this.ticketId = "no such ticket",
      );
  }

  onNameKeyUp(ev: any){
    this.ticketId = ev.target.value;
  }

}
