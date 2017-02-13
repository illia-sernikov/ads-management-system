import 'rxjs/add/operator/map';
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { BASE_API_URL } from '../constants';
import { User } from '../domain';
import { UserServiceInterface } from './user-service.interface';

const PUBLISHERS_API_URL = `${BASE_API_URL}/publishers`;

@Injectable()
export class OperatorService implements UserServiceInterface {

  constructor(private http: Http) {
  }

  getAll(): Observable<User[]> {
    return this.http.get(PUBLISHERS_API_URL)
               .map(response => response.json() as User[]);
  }

  create(publisher: User): Observable<User> {
    return this.http.post(PUBLISHERS_API_URL, publisher)
               .map(response => response.json() as User);
  }

  update(publisher: User): Observable<User> {
    const url = `${PUBLISHERS_API_URL}/${publisher.key}`;
    return this.http.put(url, publisher)
               .map(response => response.json() as User);
  }

  delete(publisher: User): Observable<User> {
    const url = `${PUBLISHERS_API_URL}/${publisher.key}`;
    return this.http.delete(url)
               .map(response => response.json() as User);
  }
}
