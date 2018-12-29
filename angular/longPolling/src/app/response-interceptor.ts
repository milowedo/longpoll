import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {TokenStorage} from './TokenStorage';


@Injectable()
export class HttpResponseInterceptor implements HttpInterceptor {

  constructor(private token: TokenStorage){}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    return next.handle(request).pipe(
      tap((ev: HttpEvent<any>) => {
        if( ev instanceof HttpResponse){
          console.log("processing response", ev);
          const token = ev.headers.get('X-AUTH-TOKEN');
          if(token){
            console.log('acquired token:  ' + token);
            this.token.save(token);
          }
        }
      })
    );
  }
}
