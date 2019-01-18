import {EventEmitter, Inject, Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Employee} from '../../entities/employee.model';
import {Observable} from 'rxjs';

@Injectable()
export class LongPoll {
  private ApiCall:string;
  private ApiURI:string;
  private subscriptionActive:boolean;
  private result:any;

  dataEmitter = new EventEmitter<any>();

  constructor(@Inject(HttpClient) private httpClient: HttpClient){}
  public setUri(uri:string){this.ApiURI = uri}
  public setCall(call:string){this.ApiCall = call}

  public changeSubscriptionStatus(val: boolean) : void {
    this.subscriptionActive = val;
  }


  public makeLongRequest(): void{
    if(this.subscriptionActive) {
      this
        .httpClient.get(this.ApiURI + this.ApiCall)
        .subscribe(
          (data : any) => {
            if(this.subscriptionActive) {
              this.setResult(data);
              console.log("received" + this.result);
            }
          },
          (data: HttpErrorResponse) => this.makeLongRequest(),
          () => this.makeLongRequest(),
        );
    }
    return this.result;
  }

  private setResult(arg : boolean){
    this.result = arg;
    this.dataEmitter.emit(this.result);
  }
}
