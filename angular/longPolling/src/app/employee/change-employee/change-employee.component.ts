import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-change-employee',
  templateUrl: './change-employee.component.html'
})
export class ChangeEmployeeComponent{

  constructor(private httpClient:HttpClient){}

  employeeId: string = '5';

  updateProfile() {
    let num: string =  'http://localhost:8080/employee/trigger/' + this.employeeId;
    this.httpClient.get(num)
      .subscribe(
        (data: any) => {
          console.log(data);
        },
        () => this.employeeId = "wrong id",
      );
  }

  onNameKeyUp(ev: any){
    this.employeeId = ev.target.value;
  }
}
