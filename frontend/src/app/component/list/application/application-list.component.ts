import 'rxjs/add/operator/toPromise';
import { Component, Input } from '@angular/core';
import { Application } from '../../../domain';
import { ApplicationService } from '../../../service';

@Component({
  selector: 'ams-application-list',
  templateUrl: 'application-list.component.html',
  styleUrls: ['application-list.component.css']
})
export class ApplicationListComponent {

  @Input() applications: Application[] = [];

  constructor(private appService: ApplicationService) {
  }

  onEditApp(app: Application): void {
    console.error('Not implemented yet');
  }

  onDeleteApp(app: Application): void {
    this.appService.delete(app)
        .toPromise()
        .then(() => {
          this.applications = this.applications.filter(a => a.key !== app.key);
        });
  }
}
