import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import { Injectable } from '@angular/core';
import { isPresent } from '@angular/core/src/facade/lang';
import { Http } from '@angular/http';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';
import { BASE_API_URL } from '../constants';
import { Account, User } from '../domain';

const SIGNIN_API_URL = `${BASE_API_URL}/auth/signin`;

const AUTH_TOKEN_KEY = 'authToken';

@Injectable()
export class AuthService {

  private userSubject: BehaviorSubject<User> = new BehaviorSubject(null);

  constructor(private http: Http) {
  }

  getUserStream(): Observable<User> {
    return this.userSubject.asObservable();
  }

  isSignedIn(): boolean {
    return isPresent(this.userSubject.getValue())
           && isPresent(localStorage.getItem(AUTH_TOKEN_KEY));
  }

  signIn(authRequest: Account): Observable<User> {
    this.http.post(SIGNIN_API_URL, authRequest)
        .map(response => response.text())
        .toPromise()
        .then(token => {
          let account = this.decodeToken(token);
          if (account) {
            let authToken = btoa(`${account.email}:${account.password}`);
            localStorage.setItem(AUTH_TOKEN_KEY, authToken);
          }
        });

    return this.getUserStream();
  }

  signOut(): Observable<null> {
    this.userSubject.next(null);
    localStorage.removeItem(AUTH_TOKEN_KEY);
    return this.getUserStream();
  }

  private decodeToken(token: string): Account {
    if (token) {
      const json = atob(token);
      return JSON.parse(json);
    }
    return null;
  }
}
