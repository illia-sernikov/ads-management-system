import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import { Injectable } from '@angular/core';
import { isPresent } from '@angular/core/src/facade/lang';
import { Http } from '@angular/http';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';
import { BASE_API_URL } from '../constants';
import { Account } from '../domain';

const SIGNIN_API_URL = `${BASE_API_URL}/auth/signin`;

const AUTH_TOKEN_KEY = 'authToken';
const CURRENT_USER_KEY = 'currentUser';

@Injectable()
export class AuthService {

  private userSubject: BehaviorSubject<Account> = new BehaviorSubject(null);

  constructor(private http: Http) {
    this.signInAtStartUp();
  }

  getUserStream(): Observable<Account> {
    return this.userSubject.asObservable();
  }

  isSignedIn(): boolean {
    return isPresent(this.userSubject.getValue())
           && isPresent(localStorage.getItem(AUTH_TOKEN_KEY));
  }

  signIn(authRequest: Account): Observable<Account> {
    this.http.post(SIGNIN_API_URL, authRequest)
        .map(response => response.text())
        .toPromise()
        .then(token => {
          const account = this.decodeToken(token);
          localStorage.setItem(CURRENT_USER_KEY, token);

          if (account) {
            const authToken = btoa(`${account.email}:${account.password}`);
            localStorage.setItem(AUTH_TOKEN_KEY, authToken);
          }
          this.userSubject.next(account);
        });

    return this.getUserStream();
  }

  signOut(): Observable<null> {
    this.userSubject.next(null);
    localStorage.removeItem(AUTH_TOKEN_KEY);
    localStorage.removeItem(CURRENT_USER_KEY);
    return this.getUserStream();
  }

  private signInAtStartUp(): void {
    const userToken = localStorage.getItem(CURRENT_USER_KEY);
    if (isPresent(userToken)) {
      this.userSubject.next(this.decodeToken(userToken));
    }
  }

  private decodeToken(token: string): Account {
    if (token) {
      const json = atob(token);
      return JSON.parse(json);
    }
    return null;
  }
}
