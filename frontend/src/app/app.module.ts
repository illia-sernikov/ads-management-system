import 'hammerjs';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Http, HttpModule, RequestOptions, XHRBackend } from '@angular/http';
import { MaterialModule } from '@angular/material';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AppRouterModule } from './app.router';
import { AdminComponent, CreateUserFormComponent, ErrorComponent } from './component';
import { AmsHttp, UserService } from './service';

export function httpFactory(backend: XHRBackend, options: RequestOptions) {
  return new AmsHttp(backend, options);
}


@NgModule({
  declarations: [
    AppComponent, AdminComponent, ErrorComponent,
    CreateUserFormComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    MaterialModule.forRoot(),

    AppRouterModule
  ],
  providers: [
    {
      provide: Http,
      useFactory: httpFactory,
      deps: [XHRBackend, RequestOptions]
    },
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
