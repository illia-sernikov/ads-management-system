import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import { Observable } from 'rxjs/Observable';
import { BASE_API_URL } from '../constants';
import { User } from '../domain';
import { UserServiceInterface } from './user-service.interface';

const USERS_API_URL = `${BASE_API_URL}/users`;

@Injectable()
export class UserService implements UserServiceInterface {

  constructor(private http: Http) {
  }

  getAll(): Observable<User[]> {
    return this.http.get(USERS_API_URL)
               .map(response => response.json() as User[]);
  }

  create(user: User): Observable<User> {
    return this.http.post(USERS_API_URL, user)
               .map(response => response.json() as User);
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
               .map(response => response.json() as User);
  }

  delete(user: User): Observable<void> {
    const url = `${USERS_API_URL}/${user.key}`;

    return this.http.delete(url)
               .map(response => undefined);
  }
}
