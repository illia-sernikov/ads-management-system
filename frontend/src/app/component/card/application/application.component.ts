import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import 'rxjs/add/operator/take';
import { Application, APPLICATION_TYPES, AppType, CONTENT_TYPES, ContentType } from '../../../domain';
import { ApplicationService } from '../../../service';

@Component({
  selector: 'ams-application',
  templateUrl: './application.component.html',
  styleUrls: ['./application.component.scss']
})
export class ApplicationComponent implements OnInit {

  @Input() application: Application;
  @Output() onDeleteApplication: EventEmitter<Application>;

  appTypes: AppType[] = APPLICATION_TYPES;
  contentTypes: ContentType[] = CONTENT_TYPES;

  private editModeEnabled = false;

  constructor(private appService: ApplicationService) {
    this.onDeleteApplication = new EventEmitter();
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
    this.onDeleteApplication.emit(this.application);
  }

  onUpdate(appForm: NgForm): void {
    const updatedApp = Object.assign({}, this.application, appForm.value);
    this.appService.update(updatedApp)
        .take(1)
        .subscribe(app => {
          this.application = app;
          this.editModeEnabled = false;
        });
  }

  onCancelUpdate(): void {
    this.editModeEnabled = false;
  }
}
