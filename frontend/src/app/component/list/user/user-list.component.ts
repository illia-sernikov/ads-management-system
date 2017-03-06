import 'rxjs/add/operator/toPromise';
import { Component, Inject, Input, OnInit } from '@angular/core';
import { isPresent } from '@angular/core/src/facade/lang';
import { Router } from '@angular/router';
import { User } from '../../../domain';
import { UserServiceInterface } from '../../../service';
import { ErrorService } from '../../../service/error.service';

@Component({
  selector: 'ams-user-list',
  templateUrl: 'user-list.component.html',
  styleUrls: ['user-list.component.scss']
})
export class UserListComponent implements OnInit {

  @Input() users: User[] = [];

  constructor(@Inject(UserServiceInterface) private userService: UserServiceInterface,
              private router: Router, private errorService: ErrorService) {
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
        })
        .catch(errorMessage => this.errorService.logError(errorMessage));
  }

  openUserDetails(user: User): void {
    if (isPresent(user) && user.role === 'PUBLISHER') {
      this.router.navigate(['/publisher', user.key]);
    }
  }
}
