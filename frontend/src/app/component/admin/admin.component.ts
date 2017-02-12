import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { UserService } from '../../service';
import { User } from '../../domain';
import 'rxjs/add/operator/takeWhile';
import 'rxjs/add/operator/toPromise';

@Component({
  selector: 'ams-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit, OnDestroy {

  users: User[] = [];

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

  createUser(userForm: NgForm) {
    const newUser = userForm.value as User;

    this.userService.createUser(newUser)
        .toPromise()
        .then(user => this.users.push(user));

    userForm.reset();
  }

}
