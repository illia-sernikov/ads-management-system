import 'rxjs/add/operator/takeWhile';
import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { User, UserRole } from '../../domain';
import { OperatorService, UserServiceInterface } from '../../service';

@Component({
  selector: 'ams-operator',
  templateUrl: './operator.component.html',
  styleUrls: ['./operator.component.css'],
  providers: [
    {
      provide: UserServiceInterface,
      useClass: OperatorService
    }
  ]
})
export class OperatorComponent implements OnInit, OnDestroy {

  operators: User[] = [];
  userRoles: UserRole[] = ['PUBLISHER'];

  private active = true;

  constructor(@Inject(UserServiceInterface) private operatorService: UserServiceInterface) {
  }

  ngOnInit() {
    this.operatorService.getAll()
        .takeWhile(() => this.active)
        .subscribe(users => this.operators = users);
  }

  ngOnDestroy(): void {
    this.active = false;
  }

  onOperatorCreated(operator: User): void {
    this.operators.push(operator);
  }
}
