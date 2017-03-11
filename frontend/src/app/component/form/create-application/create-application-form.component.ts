import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MdCheckboxChange } from '@angular/material';
import 'rxjs/add/operator/toPromise';
import { Application, APPLICATION_TYPES, ApplicationRequest, AppType, CONTENT_TYPES, ContentType, User } from '../../../domain';
import { ApplicationService } from '../../../service';

@Component({
  selector: 'ams-create-application-form',
  templateUrl: 'create-application-form.component.html',
  styleUrls: ['create-application-form.component.scss']
})
export class CreateApplicationFormComponent {

  @Input() publishers: User[] = [];
  @Input() publisherKey: string;
  @Output() onApplicationCreated: EventEmitter<Application> = new EventEmitter();

  appTypes: AppType[] = APPLICATION_TYPES;
  contentTypes: ContentType[] = CONTENT_TYPES;

  private selectedContentTypes: ContentType[] = [];

  constructor(private appService: ApplicationService) {
  }

  createApp(appForm: NgForm): void {
    const newApp = appForm.value as ApplicationRequest;
    newApp.contentTypes = this.selectedContentTypes;
    newApp.publisherKey = this.publisherKey || newApp.publisherKey;

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
