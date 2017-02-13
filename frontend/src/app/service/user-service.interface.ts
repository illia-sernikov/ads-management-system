import { OpaqueToken } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { User } from '../domain';

export interface UserServiceInterface {

  getAll(): Observable<User[]>;

  create(user: User): Observable<User>;

  update(user: User): Observable<User>;

  delete(user: User): Observable<User>;
}

export const UserServiceInterface = new OpaqueToken('UserServiceInterface');
