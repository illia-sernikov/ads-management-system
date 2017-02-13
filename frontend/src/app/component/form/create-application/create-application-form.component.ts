import 'rxjs/add/operator/toPromise';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MdCheckboxChange } from '@angular/material';
import { Application, ApplicationRequest, AppType, ContentType, User } from '../../../domain';
import { ApplicationService } from '../../../service';

@Component({
  selector: 'ams-create-application-form',
  templateUrl: 'create-application-form.component.html',
  styleUrls: ['create-application-form.component.css']
})
export class CreateApplicationFormComponent {

  @Input() publishers: User[] = [];
  @Output() onApplicationCreated: EventEmitter<Application> = new EventEmitter();

  appTypes: AppType[] = ['WEBSITE', 'ANDROID', 'IOS'];
  contentTypes: ContentType[] = ['HTML', 'IMAGE', 'VIDEO'];

  private selectedContentTypes: ContentType[] = [];

  constructor(private appService: ApplicationService) {
  }

  createApp(appForm: NgForm): void {
    const newApp = appForm.value as ApplicationRequest;
    newApp.contentTypes = this.selectedContentTypes;

    this.appService.create(newApp)
        .toPromise()
        .then(app => this.onApplicationCreated.emit(app));

    appForm.reset();
  }

  onContentTypeSelected(change: MdCheckboxChange): void {
    const selectedContentType = change.source.name as ContentType;
    const containsType = this.selectedContentTypes.some(type => type === selectedContentType);

    if (containsType) {
      this.selectedContentTypes = this.selectedContentTypes
                                      .filter(type => type !== selectedContentType);
    } else {
      this.selectedContentTypes.push(selectedContentType);
    }
  }
}
