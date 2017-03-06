import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Subject } from 'rxjs/Subject';

@Injectable()
export class ErrorService {

  private errorMessageSubject: Subject<string>;

  constructor() {
    this.errorMessageSubject = new Subject();
  }

  getErrorMessageStream(): Observable<string> {
    return this.errorMessageSubject.asObservable();
  }

  logError(errorMessage: string): void {
    this.errorMessageSubject.next(errorMessage);
  }
}
