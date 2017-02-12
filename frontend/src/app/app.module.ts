import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Http, HttpModule, RequestOptions, XHRBackend } from '@angular/http';
import { MaterialModule } from '@angular/material';

import { AppComponent } from './app.component';
import { AmsHttp, UserService } from './service';

import 'hammerjs';
import { AppRouterModule } from './app.router';
import { AdminComponent, ErrorComponent } from './component';

@NgModule({
  declarations: [
    AppComponent, AdminComponent, ErrorComponent
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
      useFactory: (backend: XHRBackend, options: RequestOptions) => {
        return new AmsHttp(backend, options);
      },
      deps: [XHRBackend, RequestOptions]
    },
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
