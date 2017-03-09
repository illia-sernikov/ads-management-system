import { Component, Inject, Input } from '@angular/core';
import 'rxjs/add/operator/toPromise';
import { User } from '../../../domain';
import { UserServiceInterface } from '../../../service';
import { ErrorService } from '../../../service/error.service';

@Component({
  selector: 'ams-user-list',
  templateUrl: 'user-list.component.html',
  styleUrls: ['user-list.component.scss']
})
export class UserListComponent {

  @Input() users: User[] = [];

  constructor(@Inject(UserServiceInterface) private userService: UserServiceInterface,
              private errorService: ErrorService) {
  }

  onDeleteUser(user: User): void {
    this.userService.delete(user)
        .toPromise()
        .then(() => {
          this.users = this.users.filter(usr => usr.key !== user.key);
        })
        .catch(errorMessage => this.errorService.logError(errorMessage));
  }
}
