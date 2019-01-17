import { Component } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Employee} from '../../entities/employee.model';

@Component({
  selector: 'app-subscribe-employee',
  templateUrl: './subscribe-employee.component.html'
})
export class SubscribeEmployeeComponent {

  received:Employee  = null;
  subscribing: boolean = false;
  num: string =  'http://localhost:8080/employee/subscribe'

  constructor(private httpClient: HttpClient){}


  getProfile() {
    if(this.subscribing) {
      this
        .httpClient.get(this.num)
        .subscribe(
          (data: Employee) => {
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
