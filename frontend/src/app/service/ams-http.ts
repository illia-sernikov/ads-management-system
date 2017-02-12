import { Injectable } from '@angular/core';
import { ConnectionBackend, Headers, Http, RequestOptions, RequestOptionsArgs, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { isPresent } from '@angular/core/src/facade/lang';

@Injectable()
export class AmsHttp extends Http {

  constructor(_backend: ConnectionBackend, _defaultOptions: RequestOptions) {
    super(_backend, _defaultOptions);
  }

  get(url: string, options?: RequestOptionsArgs): Observable<Response> {
    const opt = this.getUpdatedOptions(options);
    return super.get(url, opt);
  }

  post(url: string, body: any, options?: RequestOptionsArgs): Observable<Response> {
    const opt = this.getUpdatedOptions(options);
    return super.post(url, body, opt);
  }

  put(url: string, body: any, options?: RequestOptionsArgs): Observable<Response> {
    const opt = this.getUpdatedOptions(options);
    return super.put(url, body, opt);
  }

  delete(url: string, options?: RequestOptionsArgs): Observable<Response> {
    const opt = this.getUpdatedOptions(options);
    return super.delete(url, opt);
  }

  private getUpdatedOptions(options?: RequestOptionsArgs): RequestOptionsArgs {
    const headers = options ? new Headers(options.headers) : new Headers();
    headers.append('Content-Type', 'application/json');

    let updatedOptions = options;

    if (isPresent(updatedOptions)) {
      updatedOptions.headers = headers;
    } else {
      updatedOptions = {headers};
    }

    return updatedOptions;
  }
}
