import { Component } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';

interface customer {
  "id":number,
  "firstName":string
  "lastName":string,
  "email":string,
}

@Component({
  selector: 'app-subscribe-component',
  templateUrl: './subscribe-component.component.html',
  styleUrls: ['./subscribe-component.component.css'],
})
export class SubscribeComponentComponent {

  name: number;
  received:customer  = null;
  subscribing: boolean = false;
  num: string =  'http://localhost:8080/api/subscribe'

  constructor(private httpClient: HttpClient){}

  onNameKeyUp(event: any){
    console.log(event.target.value);
    this.name = event.target.value;
  }

  getProfile() {
    if(this.subscribing) {
      this.httpClient.get(this.num)
        .subscribe(
          (data: customer) => {
            this.received = data;
            console.log("received" + this.received);
          },
          (data : HttpErrorResponse) => this.getProfile(),
          () => this.getProfile(),
        );
    }
  }

  removeProfile() {
    this.received = null;
  }

  subscribe(val: boolean) {
    this.subscribing = val;
    this.getProfile();
  }
}
