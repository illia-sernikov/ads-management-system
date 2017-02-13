import 'rxjs/add/operator/takeWhile';
import 'rxjs/add/operator/toPromise';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { User, UserRole } from '../../domain';
import { UserService, UserServiceInterface } from '../../service';

@Component({
  selector: 'ams-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
  providers: [
    {
      provide: 'UserServiceInterface',
      useClass: UserService
    }
  ]
})
export class AdminComponent implements OnInit, OnDestroy {

  users: User[] = [];
  userRoles: UserRole[] = ['OPERATOR', 'PUBLISHER'];

  private active = true;

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.userService.getAll()
        .takeWhile(() => this.active)
        .subscribe(users => this.users = users);
  }

  ngOnDestroy(): void {
    this.active = false;
  }

  onUserCreated(user: User): void {
    this.users.push(user);
  }

}
