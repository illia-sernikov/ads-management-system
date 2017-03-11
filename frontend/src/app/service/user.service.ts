import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import { Observable } from 'rxjs/Observable';
import 'rxjs/observable/throw';
import { BASE_API_URL } from '../constants';
import { Error, User } from '../domain';
import { ErrorService } from './error.service';
import { UserServiceInterface } from './user-service.interface';

const USERS_API_URL = `${BASE_API_URL}/users`;

@Injectable()
export class UserService implements UserServiceInterface {

  constructor(private http: Http, private errorService: ErrorService) {
  }

  getAll(): Observable<User[]> {
    return this.http.get(USERS_API_URL)
               .map(response => response.json() as User[]);
  }

  create(user: User): Observable<User> {
    return this.http.post(USERS_API_URL, user)
               .map(response => response.json() as User)
               .catch(error => this.handleError(error));
  }

  update(user: User): Observable<User> {
    let userTypePart;
    switch (user.role) {
      case 'OPERATOR':
        userTypePart = 'operators';
        break;
      case 'PUBLISHER':
        userTypePart = 'publishers';
        break;
    }

    const url = `${BASE_API_URL}/${userTypePart}/${user.key}`;

    return this.http.put(url, user)
               .map(response => response.json() as User)
               .catch(error => this.handleError(error));
  }

  delete(user: User): Observable<void> {
    const url = `${USERS_API_URL}/${user.key}`;

    return this.http.delete(url)
               .map(response => undefined);
  }

  private handleError(error: Error) {
    this.errorService.logError(error.message);
    return Observable.throw(error);
  }
}
