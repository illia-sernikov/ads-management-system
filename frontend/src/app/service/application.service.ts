import 'rxjs/add/operator/map';
import { Injectable } from '@angular/core';
import { Http, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { BASE_API_URL } from '../constants';
import { Application, ApplicationRequest } from '../domain';

const APPLICATIONS_API_URL = `${BASE_API_URL}/applications`;

@Injectable()
export class ApplicationService {

  constructor(private http: Http) {
  }

  getAll(): Observable<Application[]> {
    return this.http.get(APPLICATIONS_API_URL)
               .map(response => response.json() as Application[]);
  }

  getAllPublisherApps(publisherKey: string): Observable<Application[]> {
    const params = new URLSearchParams();
    params.append('publisherKey', publisherKey);

    return this.http.get(APPLICATIONS_API_URL, {search: params})
               .map(response => response.json() as Application[]);
  }

  create(newApp: ApplicationRequest): Observable<Application> {
    return this.http.post(APPLICATIONS_API_URL, newApp)
               .map(response => response.json() as Application);
  }

  update(app: Application): Observable<Application> {
    const url = `${APPLICATIONS_API_URL}/${app.key}`;
    return this.http.put(url, app)
               .map(response => response.json() as Application);
  }

  delete(app: Application): Observable<void> {
    const url = `${APPLICATIONS_API_URL}/${app.key}`;
    return this.http.delete(url)
               .map(response => undefined);
  }
}
