import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { User } from './domain';
import { AuthService } from './service/auth.service';
import { ErrorService } from './service/error.service';

@Component({
  selector: 'ams-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  user$: Observable<User>;
  error: string = '';

  constructor(private authService: AuthService,
              private errorService: ErrorService) {
  }

  ngOnInit(): void {
    this.user$ = this.authService.getUserStream();

    this.errorService.getErrorMessageStream()
        .subscribe(error => this.error = error);
  }

  signOut(): void {
    this.authService.signOut();
  }

  closeErrorMessage(): void {
    this.error = '';
  }
}
