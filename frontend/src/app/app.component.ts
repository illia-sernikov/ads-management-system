import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Account } from './domain/user.interface';
import { AuthService } from './service/auth.service';

@Component({
  selector: 'ams-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  user$: Observable<Account>;

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
    this.user$ = this.authService.getUserStream();
  }

  signOut(): void {
    this.authService.signOut();
  }
}
