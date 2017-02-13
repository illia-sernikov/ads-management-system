import { Component, Input, OnInit } from '@angular/core';
import { User } from '../../../domain';

@Component({
  selector: 'ams-user-list',
  templateUrl: 'user-list.component.html',
  styleUrls: ['user-list.component.css']
})
export class UserListComponent implements OnInit {

  @Input() users: User[] = [];

  constructor() {
  }

  ngOnInit() {
  }
}
