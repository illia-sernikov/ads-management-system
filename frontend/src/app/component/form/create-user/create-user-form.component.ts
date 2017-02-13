import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import { User, UserRole } from '../../../domain';
import { UserService } from '../../../service';

@Component({
  selector: 'ams-create-user-form',
  templateUrl: 'create-user-form.component.html',
  styleUrls: ['create-user-form.component.css']
})
export class CreateUserFormComponent implements OnInit {

  @Input() userRoles: UserRole[] = [];
  @Output() onUserCreated: EventEmitter<User> = new EventEmitter();

  constructor(private userService: UserService) {
  }

  ngOnInit() {
  }

  createUser(userForm: NgForm) {
    const newUser = userForm.value as User;

    this.userService.createUser(newUser)
        .toPromise()
        .then(user => this.onUserCreated.emit(user));

    userForm.reset();
  }
}
