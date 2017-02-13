import 'rxjs/add/operator/toPromise';
import { Component, Inject, Input, OnInit } from '@angular/core';
import { User } from '../../../domain';
import { UserServiceInterface } from '../../../service';

@Component({
  selector: 'ams-user-list',
  templateUrl: 'user-list.component.html',
  styleUrls: ['user-list.component.css']
})
export class UserListComponent implements OnInit {

  @Input() users: User[] = [];

  constructor(@Inject(UserServiceInterface) private userService: UserServiceInterface) {
  }

  ngOnInit() {
  }

  onEditUser(user: User): void {
    console.error('Not implemented yet');
  }

  onDeleteUser(user: User): void {
    this.userService.delete(user)
        .toPromise()
        .then(() => {
          this.users = this.users.filter(usr => usr.key !== user.key);
        });
  }
}
