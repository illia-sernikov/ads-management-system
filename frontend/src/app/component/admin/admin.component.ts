import 'rxjs/add/operator/takeWhile';
import 'rxjs/add/operator/toPromise';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { User, UserRole } from '../../domain';
import { UserService } from '../../service';

@Component({
  selector: 'ams-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit, OnDestroy {

  users: User[] = [];
  userRoles: UserRole[] = ['OPERATOR', 'PUBLISHER'];

  private active = true;

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.userService.getAllUsers()
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
