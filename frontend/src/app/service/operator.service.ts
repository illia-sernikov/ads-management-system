import 'rxjs/add/operator/map';
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { BASE_API_URL } from '../constants';
import { User } from '../domain';

const OPERATORS_API_URL = `${BASE_API_URL}/operators`;

@Injectable()
export class OperatorService {

  constructor(private http: Http) {
  }

  getAllOperators(): Observable<User[]> {
    return this.http.get(OPERATORS_API_URL)
               .map(response => response.json() as User[]);
  }

  createOperator(operator: User): Observable<User> {
    return this.http.post(OPERATORS_API_URL, operator)
               .map(response => response.json() as User);
  }

  updateOperator(operator: User): Observable<User> {
    const url = `${OPERATORS_API_URL}/${operator.key}`;
    return this.http.put(url, operator)
               .map(response => response.json() as User);
  }

  deleteOperator(operator: User): Observable<User> {
    const url = `${OPERATORS_API_URL}/${operator.key}`;
    return this.http.delete(url)
               .map(response => response.json() as User);
  }
}
