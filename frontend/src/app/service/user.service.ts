import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { User } from '../domain';
import { Observable } from 'rxjs/Observable';

import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';

const BASE_URL = 'http://localhost:8080';
const USERS_API_URL = `${BASE_URL}/users`;

@Injectable()
export class UserService {

  constructor(private http: Http) {
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get(USERS_API_URL)
               .map(response => response.json() as User[]);
  }

  createUser(user: User): Observable<User> {
    return this.http.post(USERS_API_URL, user)
               .map(response => response.json() as User);
  }

  updateUser(user: User): Observable<User> {
    const url = `${USERS_API_URL}/${user.key}`;

    return this.http.put(url, user)
               .map(response => response.json() as User);
  }

  deleteUser(user: User): Observable<User> {
    const url = `${USERS_API_URL}/${user.key}`;

    return this.http.delete(url)
               .map(response => response.json() as User);
  }
}
