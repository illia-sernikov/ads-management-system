import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import { Injectable } from '@angular/core';
import { isPresent } from '@angular/core/src/facade/lang';
import { Http } from '@angular/http';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { Observable } from 'rxjs/Observable';
import { BASE_API_URL } from '../constants';
import { User } from '../domain';

const SIGNIN_API_URL = `${BASE_API_URL}/auth/signin`;

const AUTH_TOKEN_KEY = 'authToken';
const CURRENT_USER_KEY = 'currentUser';

@Injectable()
export class AuthService {

  private userSubject: BehaviorSubject<User> = new BehaviorSubject(null);

  constructor(private http: Http, private router: Router) {
    this.signInAtStartUp();
  }

  getUserStream(): Observable<User> {
    return this.userSubject.asObservable();
  }

  isSignedIn(): boolean {
    return isPresent(this.userSubject.getValue())
           && isPresent(localStorage.getItem(CURRENT_USER_KEY));
  }

  signIn(authRequest: User): Observable<User> {
    this.http.post(SIGNIN_API_URL, authRequest)
        .map(response => response.text())
        .toPromise()
        .then(token => {
          const account = this.decodeToken(token);

          if (account) {
            const authToken = btoa(`${account.email}:${account.password}`);
            localStorage.setItem(AUTH_TOKEN_KEY, authToken);
            localStorage.setItem(CURRENT_USER_KEY, token);
          }

          this.userSubject.next(account);
          this.redirectToUserHome(account);
        });

    return this.getUserStream();
  }

  signOut(): Observable<null> {
    this.userSubject.next(null);
    localStorage.removeItem(AUTH_TOKEN_KEY);
    localStorage.removeItem(CURRENT_USER_KEY);
    this.redirectToUserHome(null);
    return this.getUserStream();
  }

  private signInAtStartUp(): void {
    const userToken = localStorage.getItem(CURRENT_USER_KEY);
    if (isPresent(userToken)) {
      this.userSubject.next(this.decodeToken(userToken));
    }
  }

  private decodeToken(token: string): User {
    if (token) {
      const json = atob(token);
      return JSON.parse(json);
    }
    return null;
  }

  private redirectToUserHome(user: User): void {
    let url = '/';

    if (isPresent(user)) {
      const userRole = user.role;

      switch (userRole) {
        case 'ADMIN':
          url = 'admin';
          break;
        case 'OPERATOR':
          url = '/operator';
          break;
        case 'PUBLISHER':
          url = '/publisher/' + user.key;
          break;
      }
    }

    this.router.navigate([url]);
  }
}
