import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Http, HttpModule, RequestOptions, XHRBackend } from '@angular/http';
import { MaterialModule } from '@angular/material';
import { BrowserModule } from '@angular/platform-browser';
import 'hammerjs';

import { AppComponent } from './app.component';
import { AppRouterModule } from './app.router';
import {
  AdminComponent, ApplicationComponent, ApplicationListComponent, CreateApplicationFormComponent, CreateUserFormComponent, ErrorComponent,
  LoginFormComponent, OperatorComponent, PublisherComponent, UserComponent, UserListComponent
} from './component';
import { AuthAdminGuard, AuthGuard, AuthOperatorGuard, AuthPublisherGuard } from './guards';
import { AmsHttp, ApplicationService, AuthService, ErrorService, OperatorService, UserService } from './service';

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
    UserComponent,
    ApplicationComponent,
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
    ErrorService,

    AuthGuard,
    AuthAdminGuard,
    AuthOperatorGuard,
    AuthPublisherGuard,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
