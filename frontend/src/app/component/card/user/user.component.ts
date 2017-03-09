import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { isPresent } from '@angular/core/src/facade/lang';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import 'rxjs/add/operator/take';
import { User } from '../../../domain/user.interface';
import { UserService } from '../../../service/user.service';

@Component({
  selector: 'ams-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  @Input() user: User;
  @Output() onDeleteUser: EventEmitter<User>;

  private editModeEnabled = false;

  constructor(private userService: UserService, private router: Router) {
    this.onDeleteUser = new EventEmitter();
  }

  ngOnInit() {
  }

  get editable(): boolean {
    return this.editModeEnabled;
  }

  onEdit(): void {
    this.editModeEnabled = true;
  }

  onDelete(): void {
    this.onDeleteUser.emit(this.user);
  }

  onUpdate(userForm: NgForm): void {
    const updatedUser = Object.assign(this.user, userForm.value);
    this.userService.update(updatedUser)
        .take(1)
        .subscribe(() => this.editModeEnabled = false);
  }

  onCancelUpdate(): void {
    this.editModeEnabled = false;
  }

  openDetails(): void {
    if (isPresent(this.user) && this.user.role === 'PUBLISHER') {
      this.router.navigate(['/publisher', this.user.key]);
    }
  }

  shouldShowActionButtons(): boolean {
    return isPresent(this.user) && this.user.role !== 'ADMIN';
  }
}
