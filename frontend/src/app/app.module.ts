import 'hammerjs';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Http, HttpModule, RequestOptions, XHRBackend } from '@angular/http';
import { MaterialModule } from '@angular/material';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AppRouterModule } from './app.router';
import {
  AdminComponent, ApplicationListComponent, CreateApplicationFormComponent, CreateUserFormComponent, ErrorComponent, LoginFormComponent,
  OperatorComponent, PublisherComponent, UserListComponent
} from './component';
import { AuthGuard } from './guards';
import { AmsHttp, ApplicationService, AuthService, OperatorService, UserService } from './service';

export function httpFactory(backend: XHRBackend, options: RequestOptions) {
  return new AmsHttp(backend, options);
}

@NgModule({
  declarations: [
    AppComponent,
    ErrorComponent,
    CreateUserFormComponent,
    UserListComponent,

    AdminComponent,
    OperatorComponent,
    CreateApplicationFormComponent,
    ApplicationListComponent,
    PublisherComponent,
    LoginFormComponent,
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
    UserService,
    OperatorService,
    ApplicationService,
    AuthService,
    AuthGuard,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
