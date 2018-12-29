import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-change-component',
  templateUrl: './change-component.component.html',
  styleUrls: ['./change-component.component.css']
})
export class ChangeComponentComponent {

  constructor(private httpClient:HttpClient){}

  customerId: string = '';

  updateProfile() {
    let num: string =  'http://localhost:8080/api/trigger/' + this.customerId;
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
