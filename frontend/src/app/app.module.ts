import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Http, HttpModule, RequestOptions, XHRBackend } from '@angular/http';
import { MaterialModule } from '@angular/material';

import { AppComponent } from './app.component';
import { AmsHttp, UserService } from './service';

import 'hammerjs';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
];


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    MaterialModule.forRoot(),

    RouterModule.forRoot(routes)
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
