import {Component} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Employee} from '../../entities/employee.model';

@Component({
  selector: 'app-get-employee',
  templateUrl: './get-employee.component.html',
  styleUrls: [],
})
export class GetEmployeeComponent {

  name: string = '';
  received:Employee;

  constructor(private httpClient: HttpClient){}

  onNameKeyUp(event: any){
    console.log(event.target.value);
    this.name = event.target.value;
  }

  getProfile() {
    let num: string =  'http://localhost:8080/employee/employee/' + this.name;
    this.httpClient.get(num)
      .subscribe(
        (data: Employee) => {
          this.received = data;
          console.log(this.received);
        }
      );
  }
}
