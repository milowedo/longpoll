import {Inject, Injectable, InjectionToken} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {TokenStorage} from './TokenStorage';
import {timeout} from 'rxjs/operators';

export const DEFAULT_TIMEOUT = new InjectionToken<number>('defaultTimeout');

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {

  // constructor(private token: TokenStorage){}
  constructor(@Inject(DEFAULT_TIMEOUT) protected defaultTimeout: number) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const timeoutValue = Number(request.headers.get('timeout')) || this.defaultTimeout;
    // request = request.clone();
    console.log(timeoutValue);
    console.log(request);

    // if(this.token.get()){
    //   console.log('setting token in header: ' + this.token.get());
    //   const authReq = request.clone({
    //     headers: request.headers.set('X-AUTH-TOKEN', this.token.get())
    //   });
    //   return next.handle(authReq);
    // }

    return next.handle(request).pipe(timeout(timeoutValue));
  }
}
