import 'rxjs/add/operator/takeWhile';
import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { Application, User, UserRole } from '../../domain';
import { ApplicationService, OperatorService, UserServiceInterface } from '../../service';

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

  publishers: User[] = [];
  userRoles: UserRole[] = ['PUBLISHER'];
  applications: Application[] = [];

  private active = true;

  constructor(@Inject(UserServiceInterface) private operatorService: UserServiceInterface,
              private appService: ApplicationService) {
  }

  ngOnInit() {
    this.operatorService.getAll()
        .takeWhile(() => this.active)
        .subscribe(users => this.publishers = users);

    this.appService.getAll()
        .takeWhile(() => this.active)
        .subscribe(apps => this.applications = apps);
  }

  ngOnDestroy(): void {
    this.active = false;
  }

  onOperatorCreated(operator: User): void {
    this.publishers.push(operator);
  }

  onApplicationCreated(app: Application): void {
    this.applications.push(app);
  }
}
