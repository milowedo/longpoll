import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-change-employee',
  templateUrl: './change-employee.component.html'
})
export class ChangeEmployeeComponent{

  constructor(private httpClient:HttpClient){}

  customerId: string = '2';

  updateProfile() {
    let num: string =  'http://localhost:8080/employee/trigger/' + this.customerId;
    this.httpClient.get(num)
      .subscribe(
        (data: any) => {
          console.log(data);
        }
      );
  }

  onNameKeyUp(ev: any){
    this.customerId = ev.target.value;
  }
}
