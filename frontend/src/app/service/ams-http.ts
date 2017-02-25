import { Injectable } from '@angular/core';
import { isPresent } from '@angular/core/src/facade/lang';
import { ConnectionBackend, Headers, Http, RequestOptions, RequestOptionsArgs, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { Error } from '../domain';

@Injectable()
export class AmsHttp extends Http {

  constructor(_backend: ConnectionBackend, _defaultOptions: RequestOptions) {
    super(_backend, _defaultOptions);
  }

  get(url: string, options?: RequestOptionsArgs): Observable<Response | Error> {
    const opt = this.getUpdatedOptions(options);
    return super.get(url, opt)
                .catch(this.handleError);
  }

  post(url: string, body: any, options?: RequestOptionsArgs): Observable<Response | Error> {
    const opt = this.getUpdatedOptions(options);
    return super.post(url, body, opt)
                .catch(this.handleError);
  }

  put(url: string, body: any, options?: RequestOptionsArgs): Observable<Response | Error> {
    const opt = this.getUpdatedOptions(options);
    return super.put(url, body, opt);
  }

  delete(url: string, options?: RequestOptionsArgs): Observable<Response | Error> {
    const opt = this.getUpdatedOptions(options);
    return super.delete(url, opt);
  }

  private getUpdatedOptions(options?: RequestOptionsArgs): RequestOptionsArgs {
    const headers = options ? new Headers(options.headers) : new Headers();
    headers.append('Content-Type', 'application/json');

    let authToken = localStorage.getItem('authToken');
    if (isPresent(authToken)) {
      headers.append('Authorization', 'Basic ' + authToken);
    }

    let updatedOptions = options;

    if (isPresent(updatedOptions)) {
      updatedOptions.headers = headers;
    } else {
      updatedOptions = {headers};
    }

    return updatedOptions;
  }

  private handleError(response: Response) {
    const error = response.json();
    console.error(error.message, error);
    return Observable.throw(error.message);
  }
}
