import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { User } from './domain';
import { AuthService } from './service/auth.service';

@Component({
  selector: 'ams-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  user$: Observable<User>;

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
    this.user$ = this.authService.getUserStream();
  }

  signOut(): void {
    this.authService.signOut();
  }
}
