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
    console.log(timeoutValue,":",request);

    return next.handle(request).pipe(timeout(timeoutValue));
  }
}
