import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';

interface customer {
  "id":number,
  "firstName":string
  "lastName":string,
  "email":string,
}

@Component({
  selector: 'app-get-component',
  templateUrl: './get-component.component.html',
  styleUrls: ['./get-component.component.css'],
})
export class GetComponentComponent {

  name: string = '';
  received:customer;

  constructor(private httpClient: HttpClient){}

  onNameKeyUp(event: any){
    console.log(event.target.value);
    this.name = event.target.value;
  }

  getProfile() {
    let num: string =  'http://localhost:8080/api/customer/' + this.name;
    this.httpClient.get(num)
      .subscribe(
        (data: customer) => {
          this.received = data;
          console.log(this.received);
        }
      );
  }
}
