import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Application, APPLICATION_TYPES, AppType, CONTENT_TYPES, ContentType } from '../../../domain';

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

  constructor() {
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
    console.error('Not implemented yet');
  }

  onCancelUpdate(): void {
    this.editModeEnabled = false;
  }
}
