import {Component, Inject} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Employee} from '../../entities/employee.model';
import {LongPoll} from './LongPoll';

@Component({
  selector: 'app-subscribe-employee',
  templateUrl: './subscribe-employee.component.html',
  providers: [LongPoll],
})
export class SubscribeEmployeeComponent {

  received:Employee  = null;
  subscribing: boolean = false;
  CALL: string =  '/employee/subscribe';
  URI:string = 'http://localhost:8080';

  constructor(@Inject(LongPoll) private longPoll: LongPoll){
    longPoll.setUri(this.URI);
    longPoll.setCall(this.CALL);
  };

  fetch(){
    if(this.subscribing){
      this.longPoll.changeSubscriptionStatus(this.subscribing);
      this.longPoll.makeLongRequest();
      this.longPoll.dataEmitter.subscribe(
        data => this.received = data
      )
    }
  }

  removeProfile() {
    this.received = null;
  }

  subscribe(val: boolean) {
    this.subscribing = val;
    this.longPoll.changeSubscriptionStatus(val);
    this.fetch();
  }
}
