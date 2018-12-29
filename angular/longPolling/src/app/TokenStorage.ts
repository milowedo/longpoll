import {Injectable} from '@angular/core';

@Injectable()
export class TokenStorage {
  constructor(){}


  save(token){
    window.localStorage['xAuthToken'] = token;
  }

  get(){
    return window.localStorage['xAuthToken'];
  }

  destroy(){
    window.localStorage.removeItem('xAuthToken');
  }
}
