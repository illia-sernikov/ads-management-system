import { Injectable } from '@angular/core';
import { Http, URLSearchParams } from '@angular/http';
import 'rxjs/add/operator/map';
import { Observable } from 'rxjs/Observable';
import 'rxjs/observable/throw';
import { BASE_API_URL } from '../constants';
import { Application, ApplicationRequest, Error } from '../domain';
import { ErrorService } from './error.service';

const APPLICATIONS_API_URL = `${BASE_API_URL}/applications`;

@Injectable()
export class ApplicationService {

  constructor(private http: Http, private errorService: ErrorService) {
  }

  getAll(): Observable<Application[]> {
    return this.http.get(APPLICATIONS_API_URL)
               .map(response => response.json() as Application[]);
  }

  getAllPublisherApps(publisherKey: string): Observable<Application[]> {
    const params = new URLSearchParams();
    params.append('publisherKey', publisherKey);

    return this.http.get(APPLICATIONS_API_URL, {search: params})
               .map(response => response.json() as Application[])
               .catch(error => this.handleError(error));
  }

  create(newApp: ApplicationRequest): Observable<Application> {
    return this.http.post(APPLICATIONS_API_URL, newApp)
               .map(response => response.json() as Application)
               .catch(error => this.handleError(error));
  }

  update(app: Application): Observable<Application> {
    const url = `${APPLICATIONS_API_URL}/${app.key}`;
    return this.http.put(url, app)
               .map(response => response.json() as Application)
               .catch(error => this.handleError(error));
  }

  delete(app: Application): Observable<void> {
    const url = `${APPLICATIONS_API_URL}/${app.key}`;
    return this.http.delete(url)
               .map(response => undefined)
               .catch(error => this.handleError(error));
  }

  handleError(error: Error) {
    this.errorService.logError(error.message);
    return Observable.throw(error);
  }
}
