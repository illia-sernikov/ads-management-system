import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { isPresent } from '@angular/core/src/facade/lang';
import { Router } from '@angular/router';
import { User } from '../../../domain/user.interface';

@Component({
  selector: 'ams-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  @Input() user: User;
  @Output() onDelete: EventEmitter<User>;

  constructor(private router: Router) {
    this.onDelete = new EventEmitter();
  }

  ngOnInit() {
  }

  onEditUser(): void {
    console.error('Not implemented yet');
  }

  onDeleteUser(): void {
    this.onDelete.emit(this.user);
  }

  openUserDetails(): void {
    if (isPresent(this.user) && this.user.role === 'PUBLISHER') {
      this.router.navigate(['/publisher', this.user.key]);
    }
  }

  shouldShowActionButtons(): boolean {
    return isPresent(this.user) && this.user.role !== 'ADMIN';
  }
}
